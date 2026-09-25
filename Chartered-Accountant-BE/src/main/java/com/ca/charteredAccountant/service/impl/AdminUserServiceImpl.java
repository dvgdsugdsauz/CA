package com.ca.charteredAccountant.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.enums.AdminRole;
import com.ca.charteredAccountant.dao.model.AdminUserEntity;
import com.ca.charteredAccountant.dao.model.LoggedInUserDetails;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.AdminUserRepository;
import com.ca.charteredAccountant.request.AdminUserRequest;
import com.ca.charteredAccountant.request.ChangePasswordRequest;
import com.ca.charteredAccountant.response.AdminUserResponse;
import com.ca.charteredAccountant.response.DropdownResponse;
import com.ca.charteredAccountant.service.AdminUserService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

	private static final String USERNAME_TAKEN_MESSAGE = "This username is already taken";
	private static final String PASSWORD_REQUIRED_MESSAGE = "Set a password for the new user";
	private static final String OWN_ROLE_MESSAGE = "You cannot change your own role";
	private static final String OWN_DISABLE_MESSAGE = "You cannot disable your own account";
	private static final String LAST_ADMIN_MESSAGE = "At least one active administrator is required";

	private AdminUserRepository adminUserRepository;
	private PasswordEncoder passwordEncoder;

	// ========================= List =========================

	@Override
	@Transactional(readOnly = true)
	public List<AdminUserResponse> getAllUsers() {
		List<AdminUserResponse> users = adminUserRepository.findAllByOrderByFullNameAsc().stream()
				.map(AdminUserServiceImpl::mapEntityToResponse)
				.toList();
		return users.isEmpty() ? null : users;
	}

	// ========================= Dropdown (assign enquiry to) =========================

	@Override
	@Transactional(readOnly = true)
	public List<DropdownResponse> getUserDropdown() {
		List<DropdownResponse> users = adminUserRepository.findAllByEnabledOrderByFullNameAsc(Constants.ACTIVE).stream()
				.map(u -> new DropdownResponse(u.getId(), u.getFullName()))
				.toList();
		return users.isEmpty() ? null : users;
	}

	// ========================= Save (create / update) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(AdminUserRequest request, LoggedInUserDetails loggedInUserDetails) throws CAException {

		String username = request.getUsername().trim();
		String password = CommonUtil.trimToNull(request.getPassword()) == null ? null : request.getPassword();
		boolean enabled = request.getEnabled() == null || Boolean.TRUE.equals(request.getEnabled());

		AdminUserEntity entity;
		if (request.getUserId() == null) {
			if (password == null) {
				throw CAException.badRequest(PASSWORD_REQUIRED_MESSAGE);
			}
			if (adminUserRepository.existsByUsername(username)) {
				throw CAException.conflict(USERNAME_TAKEN_MESSAGE);
			}
			entity = new AdminUserEntity();
		} else {
			entity = adminUserRepository.findById(request.getUserId()).orElse(null);
			if (entity == null) {
				return null;
			}
			if (adminUserRepository.existsByUsernameAndIdNot(username, entity.getId())) {
				throw CAException.conflict(USERNAME_TAKEN_MESSAGE);
			}
			if (entity.getId().equals(loggedInUserDetails.getUserId())) {
				if (entity.getRole() != request.getRole()) {
					throw CAException.badRequest(OWN_ROLE_MESSAGE);
				}
				if (!enabled) {
					throw CAException.badRequest(OWN_DISABLE_MESSAGE);
				}
			}
			checkLastActiveAdmin(entity, request.getRole(), enabled);
		}

		if (password != null) {
			validatePasswordStrength(password);
			entity.setPasswordHash(passwordEncoder.encode(password));
		}

		entity.setUsername(username);
		entity.setFullName(request.getFullName().trim());
		entity.setEmail(CommonUtil.normalizeEmail(request.getEmail()));
		entity.setRole(request.getRole());
		entity.setEnabled(enabled);
		adminUserRepository.save(entity);

		log.info("User '{}' {} by {}", username, request.getUserId() == null ? "created" : "updated",
				loggedInUserDetails.getUsername());
		return true;
	}

	// ========================= Delete (disable) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteUser(Long userId, LoggedInUserDetails loggedInUserDetails) throws CAException {

		AdminUserEntity entity = adminUserRepository.findById(userId).orElse(null);
		if (entity == null) {
			return false;
		}
		if (entity.getId().equals(loggedInUserDetails.getUserId())) {
			throw CAException.badRequest(OWN_DISABLE_MESSAGE);
		}
		checkLastActiveAdmin(entity, entity.getRole(), false);

		// No hard delete: enquiries keep a reference to the user.
		entity.setEnabled(Constants.INACTIVE);
		adminUserRepository.save(entity);

		log.info("User '{}' disabled by {}", entity.getUsername(), loggedInUserDetails.getUsername());
		return true;
	}

	// ========================= Rules =========================

	private void checkLastActiveAdmin(AdminUserEntity current, AdminRole newRole, boolean newEnabled) throws CAException {
		boolean isActiveAdmin = AdminRole.ADMIN == current.getRole() && Boolean.TRUE.equals(current.getEnabled());
		boolean staysActiveAdmin = AdminRole.ADMIN == newRole && newEnabled;
		if (isActiveAdmin && !staysActiveAdmin
				&& adminUserRepository.countByRoleAndEnabled(AdminRole.ADMIN, Constants.ACTIVE) <= 1) {
			throw CAException.conflict(LAST_ADMIN_MESSAGE);
		}
	}

	private void validatePasswordStrength(String password) throws CAException {
		if (password.length() < ChangePasswordRequest.PASSWORD_MIN || password.length() > ChangePasswordRequest.PASSWORD_MAX) {
			throw CAException.badRequest(ChangePasswordRequest.PASSWORD_SIZE_MESSAGE);
		}
		if (!password.matches(ChangePasswordRequest.PASSWORD_PATTERN)) {
			throw CAException.badRequest(ChangePasswordRequest.PASSWORD_PATTERN_MESSAGE);
		}
	}

	// ========================= Mapping =========================

	static AdminUserResponse mapEntityToResponse(AdminUserEntity e) {
		AdminUserResponse r = new AdminUserResponse();
		r.setUserId(e.getId());
		r.setUsername(e.getUsername());
		r.setFullName(e.getFullName());
		r.setEmail(e.getEmail());
		r.setRole(e.getRole());
		r.setEnabled(e.getEnabled());
		r.setCreatedAt(e.getCreatedAt());
		r.setLastLoginAt(e.getLastLoginAt());
		return r;
	}

}

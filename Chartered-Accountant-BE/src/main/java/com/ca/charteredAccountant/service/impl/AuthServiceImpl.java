package com.ca.charteredAccountant.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.AdminUserEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.AdminUserRepository;
import com.ca.charteredAccountant.request.ChangePasswordRequest;
import com.ca.charteredAccountant.request.LoginRequest;
import com.ca.charteredAccountant.response.AdminUserResponse;
import com.ca.charteredAccountant.response.LoginResponse;
import com.ca.charteredAccountant.security.JwtTokenProvider;
import com.ca.charteredAccountant.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private static final String SAME_PASSWORD_MESSAGE = "New password must be different from the current password";

	private AuthenticationManager authenticationManager;
	private AdminUserRepository adminUserRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	// ========================= Login =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public LoginResponse login(LoginRequest request) {

		String username = request.getUsername().trim();

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

		AdminUserEntity entity = adminUserRepository.findByUsername(username)
				.orElseThrow(() -> new BadCredentialsException(Constants.ResponseMessages.INVALID_CREDENTIALS_MESSAGE));

		entity.setLastLoginAt(LocalDateTime.now());
		adminUserRepository.save(entity);

		log.info("User '{}' signed in", entity.getUsername());
		return new LoginResponse(jwtTokenProvider.generateToken(entity), jwtTokenProvider.getExpirationSeconds(),
				AdminUserServiceImpl.mapEntityToResponse(entity));
	}

	// ========================= Profile =========================

	@Override
	@Transactional(readOnly = true)
	public AdminUserResponse getProfile(Long userId) {
		return adminUserRepository.findById(userId).map(AdminUserServiceImpl::mapEntityToResponse).orElse(null);
	}

	// ========================= Change Password =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean changePassword(ChangePasswordRequest request, Long userId) throws CAException {

		AdminUserEntity entity = adminUserRepository.findById(userId).orElse(null);
		if (entity == null) {
			return null;
		}

		if (!passwordEncoder.matches(request.getCurrentPassword(), entity.getPasswordHash())) {
			throw CAException.badRequest(Constants.ResponseMessages.CURRENT_PASSWORD_WRONG_MESSAGE);
		}
		if (passwordEncoder.matches(request.getNewPassword(), entity.getPasswordHash())) {
			throw CAException.badRequest(SAME_PASSWORD_MESSAGE);
		}

		entity.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
		adminUserRepository.save(entity);

		log.info("User '{}' changed their password", entity.getUsername());
		return true;
	}

}

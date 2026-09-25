package com.ca.charteredAccountant.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.AdminUserEntity;
import com.ca.charteredAccountant.repository.AdminUserRepository;

import lombok.AllArgsConstructor;

/** Used only by the login endpoint to check the username and password. */
@Service
@AllArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

	private AdminUserRepository adminUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminUserEntity user = adminUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(Constants.ResponseMessages.INVALID_CREDENTIALS_MESSAGE));

		return User.withUsername(user.getUsername())
				.password(user.getPasswordHash())
				.disabled(!Boolean.TRUE.equals(user.getEnabled()))
				.authorities(List.of(new SimpleGrantedAuthority(Constants.ROLE_PREFIX + user.getRole().name())))
				.build();
	}

}

package com.ca.charteredAccountant.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.enums.AdminRole;
import com.ca.charteredAccountant.dao.model.AdminUserEntity;
import com.ca.charteredAccountant.repository.AdminUserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class AdminUserInitializer implements ApplicationRunner {

	private AdminUserRepository adminUserRepository;
	private PasswordEncoder passwordEncoder;
	private AppProperties appProperties;

	@Override
	@Transactional
	public void run(ApplicationArguments args) {
		if (adminUserRepository.count() > 0) {
			return;
		}

		AppProperties.Admin admin = appProperties.getAdmin();
		if (!StringUtils.hasText(admin.getUsername()) || !StringUtils.hasText(admin.getPassword())) {
			log.warn("No back-office users exist and app.admin.username / app.admin.password are not set");
			return;
		}

		adminUserRepository.save(AdminUserEntity.builder()
				.username(admin.getUsername().trim())
				.passwordHash(passwordEncoder.encode(admin.getPassword()))
				.fullName(admin.getFullName())
				.email(admin.getEmail())
				.role(AdminRole.ADMIN)
				.enabled(Constants.ACTIVE)
				.build());

		log.info("Created initial back-office user '{}'. Change its password after the first sign-in.", admin.getUsername());
	}

}

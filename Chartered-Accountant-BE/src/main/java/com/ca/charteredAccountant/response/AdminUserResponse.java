package com.ca.charteredAccountant.response;

import java.time.LocalDateTime;

import com.ca.charteredAccountant.common.enums.AdminRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminUserResponse {

	private Long userId;
	private String username;
	private String fullName;
	private String email;
	private AdminRole role;
	private Boolean enabled;
	private LocalDateTime createdAt;
	private LocalDateTime lastLoginAt;

}

package com.ca.charteredAccountant.dao.model;

import com.ca.charteredAccountant.common.enums.AdminRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInUserDetails {

	private Long userId;
	private String username;
	private String fullName;
	private AdminRole role;

	public boolean isAdmin() {
		return AdminRole.ADMIN == role;
	}

}

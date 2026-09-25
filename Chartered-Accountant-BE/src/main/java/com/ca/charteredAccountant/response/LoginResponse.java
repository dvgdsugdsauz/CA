package com.ca.charteredAccountant.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

	private String accessToken;
	private String tokenType = "Bearer";
	private long expiresIn;
	private AdminUserResponse user;

	public LoginResponse(String accessToken, long expiresIn, AdminUserResponse user) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.user = user;
	}

}

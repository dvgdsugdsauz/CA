package com.ca.charteredAccountant.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

	@NotBlank(message = "Enter your username")
	private String username;

	@NotBlank(message = "Enter your password")
	private String password;

}

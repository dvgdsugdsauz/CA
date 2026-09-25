package com.ca.charteredAccountant.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequest {

	public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d).*$";
	public static final int PASSWORD_MIN = 8;
	public static final int PASSWORD_MAX = 64;
	public static final String PASSWORD_SIZE_MESSAGE = "Password must be 8 to 64 characters";
	public static final String PASSWORD_PATTERN_MESSAGE = "Password must contain at least one letter and one number";

	@NotBlank(message = "Enter your current password")
	private String currentPassword;

	@NotBlank(message = "Enter a new password")
	@Size(min = PASSWORD_MIN, max = PASSWORD_MAX, message = PASSWORD_SIZE_MESSAGE)
	@Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_PATTERN_MESSAGE)
	private String newPassword;

}

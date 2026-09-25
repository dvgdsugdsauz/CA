package com.ca.charteredAccountant.request;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.enums.AdminRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminUserRequest {

	@Schema(description = "null to create a new user")
	private Long userId;

	@NotBlank(message = "Enter a username")
	@Size(max = 60, message = "Username can be up to 60 characters")
	@Pattern(regexp = "^[a-zA-Z0-9._-]{3,60}$",
			message = "Username must be 3 to 60 characters: letters, numbers, dot, underscore or hyphen")
	private String username;

	@NotBlank(message = "Enter the full name")
	@Size(max = 120, message = "Full name can be up to 120 characters")
	private String fullName;

	@NotBlank(message = "Enter an email address")
	@Pattern(regexp = Constants.Patterns.EMAIL, message = "Enter a valid email address, like name@company.com")
	@Size(max = 120, message = "Email can be up to 120 characters")
	private String email;

	@NotNull(message = "Select a role")
	private AdminRole role;

	@Schema(description = "Defaults to true on create")
	private Boolean enabled;

	@Schema(description = "Required when creating; on update leave empty to keep the current password")
	private String password;

}

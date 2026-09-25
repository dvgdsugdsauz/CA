package com.ca.charteredAccountant.request;

import com.ca.charteredAccountant.common.Constants;

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
public class JobApplicationRequest {

	@NotNull(message = "Select the opening you are applying for")
	private Long jobId;

	@NotBlank(message = "Enter your name")
	@Size(max = 120, message = "Name can be up to 120 characters")
	private String fullName;

	@NotBlank(message = "Enter your email address")
	@Pattern(regexp = Constants.Patterns.EMAIL, message = "Enter a valid email address, like name@company.com")
	@Size(max = 120, message = "Email can be up to 120 characters")
	private String email;

	@NotBlank(message = "Enter your phone number")
	@Pattern(regexp = Constants.Patterns.PHONE, message = "Use digits only, e.g. +91 98765 43210")
	private String phone;

	@Size(max = 80, message = "Qualification can be up to 80 characters")
	private String qualification;

	@Size(max = 4000, message = "Cover note can be up to 4000 characters")
	private String coverNote;

}

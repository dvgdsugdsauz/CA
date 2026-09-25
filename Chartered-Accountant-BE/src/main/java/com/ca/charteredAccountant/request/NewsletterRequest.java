package com.ca.charteredAccountant.request;

import com.ca.charteredAccountant.common.Constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsletterRequest {

	@NotBlank(message = "Enter your email address")
	@Pattern(regexp = Constants.Patterns.EMAIL, message = "Enter a valid email address to subscribe")
	@Size(max = 120, message = "Email can be up to 120 characters")
	private String email;

}

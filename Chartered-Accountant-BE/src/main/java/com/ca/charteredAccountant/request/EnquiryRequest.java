package com.ca.charteredAccountant.request;

import com.ca.charteredAccountant.common.Constants;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnquiryRequest {

	@NotBlank(message = "Enter your name")
	@Size(max = 120, message = "Name can be up to 120 characters")
	private String fullName;

	@NotBlank(message = "Enter a phone number so we can call you back")
	@Pattern(regexp = Constants.Patterns.PHONE, message = "Use digits only, e.g. +91 98765 43210")
	private String phone;

	@NotBlank(message = "Enter your email address")
	@Pattern(regexp = Constants.Patterns.EMAIL, message = "Enter a valid email address, like name@company.com")
	@Size(max = 120, message = "Email can be up to 120 characters")
	private String email;

	@Size(max = 160, message = "Company name can be up to 160 characters")
	private String companyName;

	@Schema(description = "firm_service id from the service dropdown; null = \"Not sure yet\"")
	private Long serviceId;

	@Schema(description = "office_location id of the city page the form was sent from; null if not a city page")
	private Long locationId;

	@Size(max = 4000, message = "Message can be up to 4000 characters")
	private String message;

	@Schema(description = "Angular route the form was sent from, e.g. / or /services/gst-registration")
	@Size(max = 255)
	private String sourcePage;

}

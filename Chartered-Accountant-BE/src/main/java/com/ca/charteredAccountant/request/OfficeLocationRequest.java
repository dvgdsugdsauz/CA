package com.ca.charteredAccountant.request;

import java.util.List;

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
public class OfficeLocationRequest {

	@Schema(description = "null to create, id to update")
	private Long locationId;

	@NotBlank(message = "Enter a city")
	@Size(max = 80, message = "City can be up to 80 characters")
	private String city;

	@Schema(description = "Optional; derived from the city when blank, e.g. hyderabad")
	@Size(max = 80, message = "URL slug can be up to 80 characters")
	private String slug;

	@Size(max = 160, message = "Heading can be up to 160 characters")
	private String heroHeading;

	private String intro;

	@Schema(description = "Neighbourhoods served, e.g. [\"Gachibowli\", \"Madhapur\"]")
	private List<String> areasServed;

	@NotBlank(message = "Enter the office address")
	@Size(max = 300, message = "Address can be up to 300 characters")
	private String addressLine;

	@Pattern(regexp = Constants.Patterns.PHONE, message = "Use digits only, e.g. +91 98765 43210")
	@Size(max = 20, message = "Phone can be up to 20 characters")
	private String phone;

	@Pattern(regexp = Constants.Patterns.EMAIL, message = "Enter a valid email address, like name@company.com")
	@Size(max = 120, message = "Email can be up to 120 characters")
	private String email;

	@Size(max = 120, message = "Office hours can be up to 120 characters")
	private String officeHours;

	@Size(max = 500, message = "Map link can be up to 500 characters")
	private String mapUrl;

	@Size(max = 160, message = "Meta title can be up to 160 characters")
	private String metaTitle;

	@Size(max = 300, message = "Meta description can be up to 300 characters")
	private String metaDescription;

	@Schema(description = "true makes this the only head office")
	private Boolean headOffice;

	private Boolean active;

	private Integer sortOrder;

}

package com.ca.charteredAccountant.request;

import java.time.LocalDate;

import com.ca.charteredAccountant.common.enums.EmploymentType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobOpeningRequest {

	@Schema(description = "null to create, id to update")
	private Long jobId;

	@NotBlank(message = "Enter a title")
	@Size(max = 160, message = "Title can be up to 160 characters")
	private String title;

	@Schema(description = "Optional; derived from the title when blank")
	@Size(max = 160, message = "URL slug can be up to 160 characters")
	private String slug;

	@Schema(description = "office_location id; null = not tied to a city")
	private Long locationId;

	@Size(max = 80, message = "Department can be up to 80 characters")
	private String department;

	@Schema(description = "e.g. 2-4 years")
	@Size(max = 60, message = "Experience can be up to 60 characters")
	private String experienceRange;

	@NotNull(message = "Select the employment type")
	private EmploymentType employmentType;

	@Size(max = 10000, message = "Description can be up to 10000 characters")
	private String description;

	private Boolean active;

	@Schema(description = "Last date to apply; null = open until closed")
	private LocalDate closesOn;

}

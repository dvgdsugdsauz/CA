package com.ca.charteredAccountant.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamMemberRequest {

	@Schema(description = "Null to create, existing id to update")
	private Long teamMemberId;

	@NotBlank(message = "Enter the full name")
	@Size(max = 120, message = "Name can be up to 120 characters")
	private String fullName;

	@NotBlank(message = "Enter the designation")
	@Size(max = 120, message = "Designation can be up to 120 characters")
	private String designation;

	@Size(max = 160, message = "Qualifications can be up to 160 characters")
	private String qualifications;

	@Size(max = 4000, message = "Bio can be up to 4000 characters")
	private String bio;

	@Size(max = 500, message = "Photo URL can be up to 500 characters")
	private String photoUrl;

	@Schema(description = "office_location id; null = not tied to a city")
	private Long locationId;

	private Boolean active;

	private Integer sortOrder;

}

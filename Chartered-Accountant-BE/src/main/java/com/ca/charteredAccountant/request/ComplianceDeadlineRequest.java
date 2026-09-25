package com.ca.charteredAccountant.request;

import java.time.LocalDate;

import com.ca.charteredAccountant.common.enums.ComplianceLaw;

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
public class ComplianceDeadlineRequest {

	@Schema(description = "Null to create, existing id to update")
	private Long deadlineId;

	@NotNull(message = "Select a due date")
	private LocalDate dueDate;

	@NotBlank(message = "Enter a title")
	@Size(max = 200, message = "Title can be up to 200 characters")
	private String title;

	@NotNull(message = "Select the law")
	private ComplianceLaw law;

	@Size(max = 200, message = "Applies to can be up to 200 characters")
	private String appliesTo;

	private Boolean active;

}

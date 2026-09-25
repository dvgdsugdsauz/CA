package com.ca.charteredAccountant.response;

import java.time.LocalDate;

import com.ca.charteredAccountant.common.enums.ComplianceLaw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComplianceDeadlineResponse {

	private Long deadlineId;
	private LocalDate dueDate;

	@Schema(description = "Two-digit day, e.g. 07")
	private String day;

	@Schema(description = "Upper-case short month, e.g. OCT")
	private String month;

	private String title;
	private ComplianceLaw law;
	private String lawLabel;
	private String appliesTo;
	private Boolean active;

	@Schema(description = "True when the due date is within the next 7 days (inclusive)")
	private Boolean dueSoon;

}

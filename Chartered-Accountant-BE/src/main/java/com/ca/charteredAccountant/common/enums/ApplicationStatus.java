package com.ca.charteredAccountant.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationStatus {

	RECEIVED("Received"),
	SHORTLISTED("Shortlisted"),
	INTERVIEW_SCHEDULED("Interview scheduled"),
	OFFERED("Offered"),
	HIRED("Hired"),
	REJECTED("Rejected");

	private final String label;
}

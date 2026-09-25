package com.ca.charteredAccountant.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnquiryStatus {

	NEW("New"),
	CONTACTED("Contacted"),
	PROPOSAL_SENT("Proposal sent"),
	CONVERTED("Converted"),
	CLOSED("Closed");

	private final String label;
}

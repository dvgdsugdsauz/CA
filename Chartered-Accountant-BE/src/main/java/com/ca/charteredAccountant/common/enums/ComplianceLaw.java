package com.ca.charteredAccountant.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComplianceLaw {

	GST("GST"),
	TDS("TDS"),
	INCOME_TAX("Income Tax"),
	ROC("ROC");

	private final String label;
}

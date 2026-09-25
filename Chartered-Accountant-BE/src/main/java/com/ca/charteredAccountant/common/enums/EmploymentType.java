package com.ca.charteredAccountant.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmploymentType {

	FULL_TIME("Full time"),
	ARTICLESHIP("Articleship"),
	INTERNSHIP("Internship");

	private final String label;
}

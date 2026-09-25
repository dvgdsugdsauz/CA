package com.ca.charteredAccountant.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ca.charteredAccountant.common.enums.EmploymentType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobOpeningResponse {

	private Long jobId;
	private String title;
	private String slug;
	private Long locationId;
	private String city;
	private String department;
	private String experienceRange;
	private EmploymentType employmentType;
	private String employmentTypeLabel;
	private String description;
	private Boolean active;
	private LocalDateTime postedAt;
	private LocalDate closesOn;

}

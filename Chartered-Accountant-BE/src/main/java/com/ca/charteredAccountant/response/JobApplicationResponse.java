package com.ca.charteredAccountant.response;

import java.time.LocalDateTime;

import com.ca.charteredAccountant.common.enums.ApplicationStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobApplicationResponse {

	private Long applicationId;
	private Long jobId;
	private String jobTitle;
	private String fullName;
	private String email;
	private String phone;
	private String qualification;
	private String coverNote;
	private ApplicationStatus status;
	private String statusLabel;
	private Boolean hasResume;
	private LocalDateTime createdAt;

}

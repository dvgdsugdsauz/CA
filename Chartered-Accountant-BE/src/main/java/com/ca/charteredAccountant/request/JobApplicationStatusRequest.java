package com.ca.charteredAccountant.request;

import com.ca.charteredAccountant.common.enums.ApplicationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobApplicationStatusRequest {

	@NotNull(message = "Application is required")
	private Long applicationId;

	@NotNull(message = "Select a status")
	private ApplicationStatus status;

}

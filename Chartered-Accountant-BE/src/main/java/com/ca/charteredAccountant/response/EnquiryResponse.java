package com.ca.charteredAccountant.response;

import java.time.LocalDateTime;

import com.ca.charteredAccountant.common.enums.EnquiryStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnquiryResponse {

	private Long enquiryId;
	private String referenceNo;
	private String fullName;
	private String email;
	private String phone;
	private String companyName;
	private Long serviceId;
	private String serviceTitle;
	private Long locationId;
	private String city;
	private String message;
	private String sourcePage;
	private EnquiryStatus status;
	private String statusLabel;
	private String internalNotes;
	private Long assignedToId;
	private String assignedToName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}

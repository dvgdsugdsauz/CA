package com.ca.charteredAccountant.request;

import com.ca.charteredAccountant.common.enums.EnquiryStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Back-office update. Send the full editable state: a null internalNotes or assignedToId
 * clears that field.
 */
@Getter
@Setter
@NoArgsConstructor
public class EnquiryUpdateRequest {

	@NotNull(message = "Enquiry id is required")
	private Long enquiryId;

	@NotNull(message = "Select a status")
	private EnquiryStatus status;

	@Size(max = 4000, message = "Notes can be up to 4000 characters")
	private String internalNotes;

	@Schema(description = "admin_user id of the owner; null = unassigned")
	private Long assignedToId;

}

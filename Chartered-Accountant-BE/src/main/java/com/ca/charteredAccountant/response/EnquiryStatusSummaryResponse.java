package com.ca.charteredAccountant.response;

import java.util.List;

import com.ca.charteredAccountant.common.enums.EnquiryStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Counts for the status filter tabs: "All" plus one entry per status, zeros included. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryStatusSummaryResponse {

	private long total;
	private List<StatusCount> statuses;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StatusCount {
		private EnquiryStatus status;
		private String label;
		private long count;
	}

}

package com.ca.charteredAccountant.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Full service page: the summary fields plus body, SEO fields, highlights and related services. */
@Getter
@Setter
@NoArgsConstructor
public class FirmServiceResponse extends FirmServiceSummaryResponse {

	private String body;
	private String metaTitle;
	private String metaDescription;
	private List<String> highlights;
	private List<FirmServiceSummaryResponse> relatedServices;

}

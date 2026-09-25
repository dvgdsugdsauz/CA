package com.ca.charteredAccountant.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Service card for the service grid and footer. */
@Getter
@Setter
@NoArgsConstructor
public class FirmServiceSummaryResponse {

	private Long serviceId;
	private String title;
	private String slug;
	private String summary;
	private String icon;
	private Long categoryId;
	private String categoryName;
	private String categorySlug;
	private Boolean featured;
	private Boolean active;
	private Integer sortOrder;

}

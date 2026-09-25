package com.ca.charteredAccountant.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndustryResponse {

	private Long industryId;
	private String name;
	private String slug;
	private String summary;
	private String icon;
	private Integer sortOrder;

}

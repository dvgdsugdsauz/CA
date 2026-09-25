package com.ca.charteredAccountant.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceCategoryResponse {

	private Long categoryId;
	private String name;
	private String slug;
	private Integer sortOrder;

}

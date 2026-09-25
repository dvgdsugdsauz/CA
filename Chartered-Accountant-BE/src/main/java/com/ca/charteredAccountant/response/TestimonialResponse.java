package com.ca.charteredAccountant.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestimonialResponse {

	private Long testimonialId;
	private String authorName;
	private String authorTitle;
	private String quote;
	private Integer rating;
	private Boolean active;
	private Integer sortOrder;

}

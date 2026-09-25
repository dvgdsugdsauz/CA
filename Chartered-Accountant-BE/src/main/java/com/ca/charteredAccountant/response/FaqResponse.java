package com.ca.charteredAccountant.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FaqResponse {

	private Long faqId;
	private String question;
	private String answer;
	private Long locationId;
	private String city;
	private Long serviceId;
	private String serviceTitle;
	private Boolean active;
	private Integer sortOrder;

}

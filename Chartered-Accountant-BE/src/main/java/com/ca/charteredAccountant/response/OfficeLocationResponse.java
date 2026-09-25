package com.ca.charteredAccountant.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OfficeLocationResponse {

	private Long locationId;
	private String city;
	private String slug;
	private String heroHeading;
	private String intro;
	private List<String> areasServed;
	private String addressLine;
	private String phone;
	private String email;
	private String officeHours;
	private String mapUrl;
	private String metaTitle;
	private String metaDescription;
	private Boolean headOffice;
	private Boolean active;
	private Integer sortOrder;

}

package com.ca.charteredAccountant.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamMemberResponse {

	private Long teamMemberId;
	private String fullName;
	private String designation;
	private String qualifications;
	private String bio;
	private String photoUrl;
	private Long locationId;
	private String city;
	private Boolean active;
	private Integer sortOrder;

}

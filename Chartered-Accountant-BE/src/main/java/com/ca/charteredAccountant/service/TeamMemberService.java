package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.TeamMemberRequest;
import com.ca.charteredAccountant.response.TeamMemberResponse;

public interface TeamMemberService {

	List<TeamMemberResponse> getActiveTeamMembers(String locationSlug);

	List<TeamMemberResponse> getAllTeamMembers();

	Boolean saveTeamMember(TeamMemberRequest request) throws CAException;

	Boolean deleteTeamMember(Long teamMemberId);

}

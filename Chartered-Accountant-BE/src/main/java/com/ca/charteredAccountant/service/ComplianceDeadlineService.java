package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.request.ComplianceDeadlineRequest;
import com.ca.charteredAccountant.response.ComplianceDeadlineResponse;

public interface ComplianceDeadlineService {

	List<ComplianceDeadlineResponse> getUpcomingDeadlines(Integer limit);

	List<ComplianceDeadlineResponse> getAllDeadlines();

	Boolean saveDeadline(ComplianceDeadlineRequest request);

	Boolean deleteDeadline(Long deadlineId);

}

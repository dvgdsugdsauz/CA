package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.FirmServiceRequest;
import com.ca.charteredAccountant.response.DropdownResponse;
import com.ca.charteredAccountant.response.FirmServiceResponse;
import com.ca.charteredAccountant.response.FirmServiceSummaryResponse;

public interface FirmServiceService {

	List<FirmServiceSummaryResponse> getAllActiveServices();

	FirmServiceResponse getServiceBySlug(String slug);

	List<DropdownResponse> getServiceDropdown();

	List<FirmServiceSummaryResponse> getAllServices();

	FirmServiceResponse getService(Long serviceId);

	Boolean saveService(FirmServiceRequest request) throws CAException;

	Boolean deleteService(Long serviceId);

}

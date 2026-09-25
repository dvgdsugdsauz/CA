package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.IndustryRequest;
import com.ca.charteredAccountant.response.IndustryResponse;

public interface IndustryService {

	List<IndustryResponse> getAllIndustries();

	Boolean saveIndustry(IndustryRequest request) throws CAException;

	Boolean deleteIndustry(Long industryId);

}

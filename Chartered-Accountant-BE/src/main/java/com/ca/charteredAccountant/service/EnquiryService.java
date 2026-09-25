package com.ca.charteredAccountant.service;

import org.springframework.data.domain.Pageable;

import com.ca.charteredAccountant.common.enums.EnquiryStatus;
import com.ca.charteredAccountant.dao.model.LoggedInUserDetails;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.EnquiryRequest;
import com.ca.charteredAccountant.request.EnquiryUpdateRequest;
import com.ca.charteredAccountant.response.EnquiryResponse;
import com.ca.charteredAccountant.response.EnquiryStatusSummaryResponse;
import com.ca.charteredAccountant.response.EnquirySubmitResponse;
import com.ca.charteredAccountant.response.PageResponse;

public interface EnquiryService {

	EnquirySubmitResponse submitEnquiry(EnquiryRequest request) throws CAException;

	PageResponse<EnquiryResponse> getAllEnquiries(EnquiryStatus status, String search, Pageable pageable);

	EnquiryStatusSummaryResponse getStatusSummary();

	EnquiryResponse getEnquiry(Long enquiryId);

	Boolean updateEnquiry(EnquiryUpdateRequest request, LoggedInUserDetails loggedInUserDetails) throws CAException;

}

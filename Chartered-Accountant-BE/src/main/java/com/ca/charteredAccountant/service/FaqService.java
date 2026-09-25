package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.FaqRequest;
import com.ca.charteredAccountant.response.FaqResponse;

public interface FaqService {

	List<FaqResponse> getActiveFaqs(String locationSlug, String serviceSlug);

	List<FaqResponse> getAllFaqs();

	Boolean saveFaq(FaqRequest request) throws CAException;

	Boolean deleteFaq(Long faqId);

}

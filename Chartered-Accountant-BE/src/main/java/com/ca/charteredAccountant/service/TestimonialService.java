package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.request.TestimonialRequest;
import com.ca.charteredAccountant.response.TestimonialResponse;

public interface TestimonialService {

	List<TestimonialResponse> getActiveTestimonials();

	List<TestimonialResponse> getAllTestimonials();

	Boolean saveTestimonial(TestimonialRequest request);

	Boolean deleteTestimonial(Long testimonialId);

}

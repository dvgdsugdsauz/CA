package com.ca.charteredAccountant.service;

import java.util.List;

import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.JobOpeningRequest;
import com.ca.charteredAccountant.response.JobOpeningResponse;

public interface JobOpeningService {

	List<JobOpeningResponse> getActiveJobOpenings();

	JobOpeningResponse getJobOpeningBySlug(String slug);

	List<JobOpeningResponse> getAllJobOpenings();

	JobOpeningResponse getJobOpening(Long jobId);

	Boolean saveJobOpening(JobOpeningRequest request) throws CAException;

	Boolean deleteJobOpening(Long jobId);

}

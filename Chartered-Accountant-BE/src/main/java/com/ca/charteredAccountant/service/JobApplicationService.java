package com.ca.charteredAccountant.service;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.ca.charteredAccountant.common.enums.ApplicationStatus;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.JobApplicationRequest;
import com.ca.charteredAccountant.request.JobApplicationStatusRequest;
import com.ca.charteredAccountant.response.JobApplicationResponse;
import com.ca.charteredAccountant.response.PageResponse;

public interface JobApplicationService {

	void applyForJob(JobApplicationRequest request, MultipartFile resume) throws CAException;

	PageResponse<JobApplicationResponse> getAllApplications(Long jobId, ApplicationStatus status, Pageable pageable);

	Boolean updateApplicationStatus(JobApplicationStatusRequest request);

	ResumeFile getResume(Long applicationId) throws CAException;

	/** A stored resume ready to stream, with the download file name and content type. */
	record ResumeFile(Resource resource, String fileName, String contentType) {
	}

}

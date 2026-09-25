package com.ca.charteredAccountant.controller;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.URLConstants;
import com.ca.charteredAccountant.common.enums.ApplicationStatus;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.JobApplicationRequest;
import com.ca.charteredAccountant.request.JobApplicationStatusRequest;
import com.ca.charteredAccountant.response.JobApplicationResponse;
import com.ca.charteredAccountant.response.PageResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.JobApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Job Application Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class JobApplicationController extends BaseController {

	private JobApplicationService jobApplicationService;

//	=========================== Apply for Job (public) ========================

	@Operation(
			summary = "Apply for Job",
			description = "Multipart request. Angular sends FormData with 'application' as a Blob of type "
					+ "application/json and 'resume' as the file. The resume must be a PDF, DOC or DOCX up to 5 MB. "
					+ "Inactive or closed openings are rejected."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.APPLICATION_SUBMITTED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.BAD_REQUEST + "",
			description = Constants.ResponseMessages.VALIDATION_FAILED_MESSAGE + " / "
					+ Constants.ResponseMessages.JOB_CLOSED_MESSAGE + " / " + Constants.ResponseMessages.INVALID_FILE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(value = URLConstants.JobApplication.APPLY_FOR_JOB, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Response> applyForJob(
			@RequestPart("application") @Validated JobApplicationRequest request,
			@RequestPart(name = "resume", required = false) MultipartFile resume) throws CAException {

		jobApplicationService.applyForJob(request, resume);

		return getOKResponseEntity(new Response(Constants.OK, Constants.ResponseMessages.APPLICATION_SUBMITTED_MESSAGE, null));
	}

//	=========================== Get All Applications (back office) ========================

	@Operation(
			summary = "Get All Applications",
			description = "Paged list of job applications, newest first. Filter by job and/or status."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.FETCH_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@GetMapping(URLConstants.JobApplication.GET_ALL_APPLICATIONS)
	public ResponseEntity<Response> getAllApplications(
			@RequestParam(required = false) Long jobId,
			@RequestParam(required = false) ApplicationStatus status,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		Response response = null;

		PageResponse<JobApplicationResponse> applications = jobApplicationService.getAllApplications(jobId, status,
				buildPageable(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));

		if (applications.getTotalElements() > 0) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, applications);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, applications);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Update Application Status ========================

	@Operation(summary = "Update Application Status", description = "Moves an application through the hiring stages.")
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.UPDATE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.JobApplication.UPDATE_APPLICATION_STATUS)
	public ResponseEntity<Response> updateApplicationStatus(@Validated @RequestBody JobApplicationStatusRequest request) {

		Response response = null;

		Boolean isUpdated = jobApplicationService.updateApplicationStatus(request);

		if (Boolean.TRUE.equals(isUpdated)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.UPDATE_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Download Resume ========================

	@Operation(
			summary = "Download Resume",
			description = "Streams the applicant's resume as an attachment. Not wrapped in the Response envelope; "
					+ "errors still come back as Response JSON."
			)
	@ApiResponse(responseCode = Constants.OK + "", description = "The resume file")
	@ApiResponse(
			responseCode = Constants.NOT_FOUND + "",
			description = "Resume not found",
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@GetMapping(URLConstants.JobApplication.DOWNLOAD_RESUME)
	public ResponseEntity<Resource> downloadResume(@PathVariable Long applicationId) throws CAException {

		JobApplicationService.ResumeFile resume = jobApplicationService.getResume(applicationId);

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(resume.contentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment().filename(resume.fileName()).build().toString())
				.body(resume.resource());
	}

}

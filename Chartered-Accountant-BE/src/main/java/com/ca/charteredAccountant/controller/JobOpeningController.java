package com.ca.charteredAccountant.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.URLConstants;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.JobOpeningRequest;
import com.ca.charteredAccountant.response.JobOpeningResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.JobOpeningService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Job Opening Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class JobOpeningController extends BaseController {

	private JobOpeningService jobOpeningService;

//	=========================== Get Active Job Openings (public) ========================

	@Operation(
			summary = "Get Active Job Openings",
			description = "Openings shown on the careers page: active and not past their closing date, newest first."
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
	@GetMapping(URLConstants.JobOpening.GET_ACTIVE_JOB_OPENINGS)
	public ResponseEntity<Response> getActiveJobOpenings() {

		Response response = null;

		List<JobOpeningResponse> openings = jobOpeningService.getActiveJobOpenings();

		if (openings != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, openings);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Job Opening by Slug (public) ========================

	@Operation(
			summary = "Get Job Opening by Slug",
			description = "One open job for its detail page. Inactive or closed openings return 204."
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
	@GetMapping(URLConstants.JobOpening.GET_JOB_OPENING_BY_SLUG)
	public ResponseEntity<Response> getJobOpeningBySlug(@PathVariable String slug) {

		Response response = null;

		JobOpeningResponse opening = jobOpeningService.getJobOpeningBySlug(slug);

		if (opening != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, opening);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get All Job Openings (back office) ========================

	@Operation(summary = "Get All Job Openings", description = "Every opening, active or not, newest first.")
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
	@GetMapping(URLConstants.JobOpening.GET_ALL_JOB_OPENINGS)
	public ResponseEntity<Response> getAllJobOpenings() {

		Response response = null;

		List<JobOpeningResponse> openings = jobOpeningService.getAllJobOpenings();

		if (openings != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, openings);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Single Job Opening ========================

	@Operation(summary = "Get Job Opening by ID", description = "One opening for the edit form.")
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
	@GetMapping(URLConstants.JobOpening.GET_JOB_OPENING_BY_ID)
	public ResponseEntity<Response> getJobOpening(@PathVariable Long jobId) {

		Response response = null;

		JobOpeningResponse opening = jobOpeningService.getJobOpening(jobId);

		if (opening != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, opening);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save Job Opening ========================

	@Operation(
			summary = "Save Job Opening",
			description = "Creates an opening when jobId is null, otherwise updates it. The slug is derived from the "
					+ "title when blank. postedAt is set on create and never changed."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.SAVE_MESSAGE + " / " + Constants.ResponseMessages.UPDATE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.CONFLICT + "",
			description = "Slug already used by another job opening",
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.JobOpening.SAVE_JOB_OPENING)
	public ResponseEntity<Response> saveJobOpening(@Validated @RequestBody JobOpeningRequest request)
			throws CAException {

		Response response = null;

		Boolean isSaved = jobOpeningService.saveJobOpening(request);

		if (Boolean.TRUE.equals(isSaved)) {
			String message = request.getJobId() == null ? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE;
			response = new Response(Constants.OK, message, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete Job Opening ========================

	@Operation(summary = "Delete Job Opening", description = "Soft delete: marks the opening inactive.")
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.DELETED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.NO_CONTENT + "",
			description = Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.JobOpening.DELETE_JOB_OPENING)
	public ResponseEntity<Response> deleteJobOpening(@RequestParam(name = "jobId") Long jobId) {

		Response response = null;

		Boolean isDeleted = jobOpeningService.deleteJobOpening(jobId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

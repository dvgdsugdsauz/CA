package com.ca.charteredAccountant.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.URLConstants;
import com.ca.charteredAccountant.request.ComplianceDeadlineRequest;
import com.ca.charteredAccountant.response.ComplianceDeadlineResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.ComplianceDeadlineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Compliance Deadline Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class ComplianceDeadlineController extends BaseController {

	private ComplianceDeadlineService complianceDeadlineService;

//	=========================== Get Upcoming Deadlines (public) ========================

	@Operation(
			summary = "Get Upcoming Deadlines",
			description = "Active due dates from today onwards, soonest first, for the home page \"Due dates ahead\" "
					+ "ledger. limit defaults to 5 (1 to 20). dueSoon is true for dates within the next 7 days."
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
	@GetMapping(URLConstants.ComplianceDeadline.GET_UPCOMING_DEADLINES)
	public ResponseEntity<Response> getUpcomingDeadlines(@RequestParam(required = false) Integer limit) {

		Response response = null;

		List<ComplianceDeadlineResponse> deadlines = complianceDeadlineService.getUpcomingDeadlines(limit);

		if (deadlines != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, deadlines);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get All Deadlines (back office) ========================

	@Operation(summary = "Get All Deadlines", description = "Every deadline, active and inactive, latest due date first.")
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
	@GetMapping(URLConstants.ComplianceDeadline.GET_ALL_DEADLINES)
	public ResponseEntity<Response> getAllDeadlines() {

		Response response = null;

		List<ComplianceDeadlineResponse> deadlines = complianceDeadlineService.getAllDeadlines();

		if (deadlines != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, deadlines);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save Deadline ========================

	@Operation(summary = "Save Deadline", description = "Creates a deadline when deadlineId is null, otherwise updates it.")
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
			responseCode = Constants.BAD_REQUEST + "",
			description = Constants.ResponseMessages.VALIDATION_FAILED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.ComplianceDeadline.SAVE_DEADLINE)
	public ResponseEntity<Response> saveDeadline(@Validated @RequestBody ComplianceDeadlineRequest request) {

		Response response = null;

		Boolean isSaved = complianceDeadlineService.saveDeadline(request);

		if (Boolean.TRUE.equals(isSaved)) {
			response = new Response(Constants.OK, request.getDeadlineId() == null
					? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete Deadline ========================

	@Operation(summary = "Delete Deadline", description = "Soft delete: marks the deadline inactive.")
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
	@PostMapping(URLConstants.ComplianceDeadline.DELETE_DEADLINE)
	public ResponseEntity<Response> deleteDeadline(@RequestParam(name = "deadlineId") Long deadlineId) {

		Response response = null;

		Boolean isDeleted = complianceDeadlineService.deleteDeadline(deadlineId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

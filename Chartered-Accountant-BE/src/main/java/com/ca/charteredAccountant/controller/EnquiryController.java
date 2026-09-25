package com.ca.charteredAccountant.controller;

import org.springframework.data.domain.Sort;
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
import com.ca.charteredAccountant.common.enums.EnquiryStatus;
import com.ca.charteredAccountant.dao.model.LoggedInUserDetails;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.EnquiryRequest;
import com.ca.charteredAccountant.request.EnquiryUpdateRequest;
import com.ca.charteredAccountant.response.EnquiryResponse;
import com.ca.charteredAccountant.response.EnquiryStatusSummaryResponse;
import com.ca.charteredAccountant.response.EnquirySubmitResponse;
import com.ca.charteredAccountant.response.PageResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.EnquiryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Enquiry Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class EnquiryController extends BaseController {

	private EnquiryService enquiryService;

//	=========================== Submit Enquiry (public) ========================

	@Operation(
			summary = "Submit Enquiry",
			description = "Saves an enquiry from the website form with status NEW and returns its reference number "
					+ "(ENQ-{year}-{6 digits}) for the thank-you page. On validation failure the response data holds "
					+ "one message per field, keyed by field name."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.ENQUIRY_SUBMITTED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.BAD_REQUEST + "",
			description = Constants.ResponseMessages.VALIDATION_FAILED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.Enquiry.SUBMIT_ENQUIRY)
	public ResponseEntity<Response> submitEnquiry(@Validated @RequestBody EnquiryRequest request) throws CAException {

		EnquirySubmitResponse submitted = enquiryService.submitEnquiry(request);

		return getOKResponseEntity(new Response(Constants.OK, Constants.ResponseMessages.ENQUIRY_SUBMITTED_MESSAGE, submitted));
	}

//	=========================== Get All Enquiries (back office) ========================

	@Operation(
			summary = "Get All Enquiries",
			description = "Paged list of enquiries, newest first. Filter by status and/or search on name, email, "
					+ "phone, company or reference number."
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
	@GetMapping(URLConstants.Enquiry.GET_ALL_ENQUIRIES)
	public ResponseEntity<Response> getAllEnquiries(
			@RequestParam(required = false) EnquiryStatus status,
			@RequestParam(required = false) String search,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		Response response = null;

		PageResponse<EnquiryResponse> enquiries = enquiryService.getAllEnquiries(status, search,
				buildPageable(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));

		if (enquiries.getTotalElements() > 0) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, enquiries);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, enquiries);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Status Counts ========================

	@Operation(summary = "Get Enquiry Status Counts", description = "Total and per-status counts for the filter tabs.")
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.FETCH_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@GetMapping(URLConstants.Enquiry.GET_ENQUIRY_STATUS_COUNTS)
	public ResponseEntity<Response> getStatusCounts() {

		EnquiryStatusSummaryResponse summary = enquiryService.getStatusSummary();

		return getOKResponseEntity(new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, summary));
	}

//	=========================== Get Single Enquiry ========================

	@Operation(summary = "Get Enquiry by ID", description = "Full detail of one enquiry, including internal notes.")
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
	@GetMapping(URLConstants.Enquiry.GET_ENQUIRY_BY_ID)
	public ResponseEntity<Response> getEnquiry(@PathVariable Long enquiryId) {

		Response response = null;

		EnquiryResponse enquiry = enquiryService.getEnquiry(enquiryId);

		if (enquiry != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, enquiry);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Update Enquiry ========================

	@Operation(
			summary = "Update Enquiry",
			description = "Sets status, internal notes and assigned team member. Send the full editable state: "
					+ "null notes or assignedToId clears them."
			)
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
	@PostMapping(URLConstants.Enquiry.UPDATE_ENQUIRY)
	public ResponseEntity<Response> updateEnquiry(@Validated @RequestBody EnquiryUpdateRequest request)
			throws CAException {

		Response response = null;

		LoggedInUserDetails loggedInUserDetails = getLoggedInUserDetails();

		Boolean isUpdated = enquiryService.updateEnquiry(request, loggedInUserDetails);

		if (Boolean.TRUE.equals(isUpdated)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.UPDATE_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

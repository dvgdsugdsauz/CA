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
import com.ca.charteredAccountant.request.FirmServiceRequest;
import com.ca.charteredAccountant.response.DropdownResponse;
import com.ca.charteredAccountant.response.FirmServiceResponse;
import com.ca.charteredAccountant.response.FirmServiceSummaryResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.FirmServiceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Firm Service Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class FirmServiceController extends BaseController {

	private FirmServiceService firmServiceService;

//	=========================== Get All Active Services (public) ========================

	@Operation(summary = "Get Active Services", description = "Active services as summary cards, by sort order, for the service grid and footer.")
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
	@GetMapping(URLConstants.FirmService.GET_ALL_ACTIVE_SERVICES)
	public ResponseEntity<Response> getAllActiveServices() {

		Response response = null;

		List<FirmServiceSummaryResponse> services = firmServiceService.getAllActiveServices();

		if (services != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, services);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Service Dropdown (public) ========================

	@Operation(summary = "Get Service Dropdown", description = "Active services as id/name pairs for the enquiry form select.")
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
	@GetMapping(URLConstants.FirmService.GET_SERVICE_DROPDOWN)
	public ResponseEntity<Response> getServiceDropdown() {

		Response response = null;

		List<DropdownResponse> services = firmServiceService.getServiceDropdown();

		if (services != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, services);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Service by Slug (public) ========================

	@Operation(
			summary = "Get Service by Slug",
			description = "Full service page for an active service, with highlights and up to 4 related services "
					+ "(same category first)."
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
	@GetMapping(URLConstants.FirmService.GET_SERVICE_BY_SLUG)
	public ResponseEntity<Response> getServiceBySlug(@PathVariable String slug) {

		Response response = null;

		FirmServiceResponse service = firmServiceService.getServiceBySlug(slug);

		if (service != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, service);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get All Services (back office) ========================

	@Operation(summary = "Get All Services", description = "Every service, active and inactive, as summary rows by sort order.")
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
	@GetMapping(URLConstants.FirmService.GET_ALL_SERVICES)
	public ResponseEntity<Response> getAllServices() {

		Response response = null;

		List<FirmServiceSummaryResponse> services = firmServiceService.getAllServices();

		if (services != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, services);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Single Service ========================

	@Operation(summary = "Get Service by ID", description = "Full detail of one service, with highlights, for the edit form.")
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
	@GetMapping(URLConstants.FirmService.GET_SERVICE_BY_ID)
	public ResponseEntity<Response> getService(@PathVariable Long serviceId) {

		Response response = null;

		FirmServiceResponse service = firmServiceService.getService(serviceId);

		if (service != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, service);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save Service ========================

	@Operation(
			summary = "Save Service",
			description = "Creates a service when serviceId is null, otherwise updates it. The slug is derived from the "
					+ "title when blank. The highlights list replaces the existing highlights completely."
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
			responseCode = Constants.BAD_REQUEST + "",
			description = Constants.ResponseMessages.VALIDATION_FAILED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.CONFLICT + "",
			description = "The URL slug is already used",
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.FirmService.SAVE_SERVICE)
	public ResponseEntity<Response> saveService(@Validated @RequestBody FirmServiceRequest request) throws CAException {

		Response response = null;

		Boolean isSaved = firmServiceService.saveService(request);

		if (Boolean.TRUE.equals(isSaved)) {
			String message = request.getServiceId() == null ? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE;
			response = new Response(Constants.OK, message, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete Service ========================

	@Operation(summary = "Delete Service", description = "Soft delete: the service is marked inactive and hidden from the website.")
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
	@PostMapping(URLConstants.FirmService.DELETE_SERVICE)
	public ResponseEntity<Response> deleteService(@RequestParam(name = "serviceId") Long serviceId) {

		Response response = null;

		Boolean isDeleted = firmServiceService.deleteService(serviceId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

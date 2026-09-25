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
import com.ca.charteredAccountant.request.OfficeLocationRequest;
import com.ca.charteredAccountant.response.OfficeLocationResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.OfficeLocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Office Location Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class OfficeLocationController extends BaseController {

	private OfficeLocationService officeLocationService;

//	=========================== Get All Active Locations (public) ========================

	@Operation(summary = "Get Active Locations", description = "Active office locations by sort order, for the city pages, contact page and footer.")
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
	@GetMapping(URLConstants.OfficeLocation.GET_ALL_ACTIVE_LOCATIONS)
	public ResponseEntity<Response> getAllActiveLocations() {

		Response response = null;

		List<OfficeLocationResponse> locations = officeLocationService.getAllActiveLocations();

		if (locations != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, locations);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Location by Slug (public) ========================

	@Operation(summary = "Get Location by Slug", description = "One active location for its city landing page, e.g. hyderabad.")
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
	@GetMapping(URLConstants.OfficeLocation.GET_LOCATION_BY_SLUG)
	public ResponseEntity<Response> getLocationBySlug(@PathVariable String slug) {

		Response response = null;

		OfficeLocationResponse location = officeLocationService.getLocationBySlug(slug);

		if (location != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, location);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get All Locations (back office) ========================

	@Operation(summary = "Get All Locations", description = "Every office location, active and inactive, by sort order.")
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
	@GetMapping(URLConstants.OfficeLocation.GET_ALL_LOCATIONS)
	public ResponseEntity<Response> getAllLocations() {

		Response response = null;

		List<OfficeLocationResponse> locations = officeLocationService.getAllLocations();

		if (locations != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, locations);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get Single Location ========================

	@Operation(summary = "Get Location by ID", description = "One office location for the edit form.")
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
	@GetMapping(URLConstants.OfficeLocation.GET_LOCATION_BY_ID)
	public ResponseEntity<Response> getLocation(@PathVariable Long locationId) {

		Response response = null;

		OfficeLocationResponse location = officeLocationService.getLocation(locationId);

		if (location != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, location);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save Location ========================

	@Operation(
			summary = "Save Location",
			description = "Creates a location when locationId is null, otherwise updates it. The slug is derived from "
					+ "the city when blank. Marking a location as head office clears the flag on all others."
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
			description = "The URL slug is already used",
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.OfficeLocation.SAVE_LOCATION)
	public ResponseEntity<Response> saveLocation(@Validated @RequestBody OfficeLocationRequest request)
			throws CAException {

		Response response = null;

		Boolean isSaved = officeLocationService.saveLocation(request);

		if (Boolean.TRUE.equals(isSaved)) {
			String message = request.getLocationId() == null ? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE;
			response = new Response(Constants.OK, message, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete Location ========================

	@Operation(summary = "Delete Location", description = "Soft delete: the location is marked inactive and hidden from the website.")
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
	@PostMapping(URLConstants.OfficeLocation.DELETE_LOCATION)
	public ResponseEntity<Response> deleteLocation(@RequestParam(name = "locationId") Long locationId) {

		Response response = null;

		Boolean isDeleted = officeLocationService.deleteLocation(locationId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

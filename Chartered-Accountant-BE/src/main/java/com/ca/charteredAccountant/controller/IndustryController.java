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
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.IndustryRequest;
import com.ca.charteredAccountant.response.IndustryResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.IndustryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Industry Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class IndustryController extends BaseController {

	private IndustryService industryService;

//	=========================== Get All Industries (public) ========================

	@Operation(summary = "Get All Industries", description = "Industries served, by sort order, for the \"Industries we serve\" section.")
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
	@GetMapping(URLConstants.Industry.GET_ALL_INDUSTRIES)
	public ResponseEntity<Response> getAllIndustries() {

		Response response = null;

		List<IndustryResponse> industries = industryService.getAllIndustries();

		if (industries != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, industries);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save Industry ========================

	@Operation(
			summary = "Save Industry",
			description = "Creates an industry when industryId is null, otherwise updates it. The slug is derived from the name when blank."
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
	@PostMapping(URLConstants.Industry.SAVE_INDUSTRY)
	public ResponseEntity<Response> saveIndustry(@Validated @RequestBody IndustryRequest request) throws CAException {

		Response response = null;

		Boolean isSaved = industryService.saveIndustry(request);

		if (Boolean.TRUE.equals(isSaved)) {
			String message = request.getIndustryId() == null ? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE;
			response = new Response(Constants.OK, message, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete Industry ========================

	@Operation(summary = "Delete Industry", description = "Permanently deletes an industry.")
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
	@PostMapping(URLConstants.Industry.DELETE_INDUSTRY)
	public ResponseEntity<Response> deleteIndustry(@RequestParam(name = "industryId") Long industryId) {

		Response response = null;

		Boolean isDeleted = industryService.deleteIndustry(industryId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

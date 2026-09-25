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
import com.ca.charteredAccountant.request.FaqRequest;
import com.ca.charteredAccountant.response.FaqResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.FaqService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "FAQ Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class FaqController extends BaseController {

	private FaqService faqService;

//	=========================== Get Active FAQs (public) ========================

	@Operation(
			summary = "Get Active FAQs",
			description = "Active FAQs for a website page, ordered by sort order. With serviceSlug: that service's FAQs. "
					+ "Otherwise general FAQs: the common ones plus, when locationSlug is given, that city's own."
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
	@GetMapping(URLConstants.Faq.GET_ACTIVE_FAQS)
	public ResponseEntity<Response> getActiveFaqs(
			@RequestParam(required = false) String locationSlug,
			@RequestParam(required = false) String serviceSlug) {

		Response response = null;

		List<FaqResponse> faqs = faqService.getActiveFaqs(locationSlug, serviceSlug);

		if (faqs != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, faqs);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get All FAQs (back office) ========================

	@Operation(summary = "Get All FAQs", description = "Every FAQ, active and inactive, with its city and service.")
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
	@GetMapping(URLConstants.Faq.GET_ALL_FAQS)
	public ResponseEntity<Response> getAllFaqs() {

		Response response = null;

		List<FaqResponse> faqs = faqService.getAllFaqs();

		if (faqs != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, faqs);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save FAQ ========================

	@Operation(
			summary = "Save FAQ",
			description = "Creates an FAQ when faqId is null, otherwise updates it. locationId and serviceId are optional."
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
	@PostMapping(URLConstants.Faq.SAVE_FAQ)
	public ResponseEntity<Response> saveFaq(@Validated @RequestBody FaqRequest request) throws CAException {

		Response response = null;

		Boolean isSaved = faqService.saveFaq(request);

		if (Boolean.TRUE.equals(isSaved)) {
			response = new Response(Constants.OK, request.getFaqId() == null
					? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete FAQ ========================

	@Operation(summary = "Delete FAQ", description = "Soft delete: marks the FAQ inactive.")
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
	@PostMapping(URLConstants.Faq.DELETE_FAQ)
	public ResponseEntity<Response> deleteFaq(@RequestParam(name = "faqId") Long faqId) {

		Response response = null;

		Boolean isDeleted = faqService.deleteFaq(faqId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

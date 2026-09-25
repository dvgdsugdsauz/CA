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
import com.ca.charteredAccountant.request.TestimonialRequest;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.response.TestimonialResponse;
import com.ca.charteredAccountant.service.TestimonialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Testimonial Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class TestimonialController extends BaseController {

	private TestimonialService testimonialService;

//	=========================== Get Active Testimonials (public) ========================

	@Operation(summary = "Get Active Testimonials", description = "Active client testimonials, ordered by sort order.")
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
	@GetMapping(URLConstants.Testimonial.GET_ACTIVE_TESTIMONIALS)
	public ResponseEntity<Response> getActiveTestimonials() {

		Response response = null;

		List<TestimonialResponse> testimonials = testimonialService.getActiveTestimonials();

		if (testimonials != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, testimonials);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get All Testimonials (back office) ========================

	@Operation(summary = "Get All Testimonials", description = "Every testimonial, active and inactive.")
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
	@GetMapping(URLConstants.Testimonial.GET_ALL_TESTIMONIALS)
	public ResponseEntity<Response> getAllTestimonials() {

		Response response = null;

		List<TestimonialResponse> testimonials = testimonialService.getAllTestimonials();

		if (testimonials != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, testimonials);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save Testimonial ========================

	@Operation(summary = "Save Testimonial", description = "Creates a testimonial when testimonialId is null, otherwise updates it.")
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
	@PostMapping(URLConstants.Testimonial.SAVE_TESTIMONIAL)
	public ResponseEntity<Response> saveTestimonial(@Validated @RequestBody TestimonialRequest request) {

		Response response = null;

		Boolean isSaved = testimonialService.saveTestimonial(request);

		if (Boolean.TRUE.equals(isSaved)) {
			response = new Response(Constants.OK, request.getTestimonialId() == null
					? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete Testimonial ========================

	@Operation(summary = "Delete Testimonial", description = "Soft delete: marks the testimonial inactive.")
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
	@PostMapping(URLConstants.Testimonial.DELETE_TESTIMONIAL)
	public ResponseEntity<Response> deleteTestimonial(@RequestParam(name = "testimonialId") Long testimonialId) {

		Response response = null;

		Boolean isDeleted = testimonialService.deleteTestimonial(testimonialId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

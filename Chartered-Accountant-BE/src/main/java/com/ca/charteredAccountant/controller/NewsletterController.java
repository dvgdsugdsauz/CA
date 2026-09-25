package com.ca.charteredAccountant.controller;

import org.springframework.data.domain.Sort;
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
import com.ca.charteredAccountant.request.NewsletterRequest;
import com.ca.charteredAccountant.response.NewsletterSubscriberResponse;
import com.ca.charteredAccountant.response.PageResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.NewsletterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Newsletter Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class NewsletterController extends BaseController {

	private NewsletterService newsletterService;

//	=========================== Subscribe (public) ========================

	@Operation(
			summary = "Subscribe to Newsletter",
			description = "Subscribes an email to tax updates. A previously unsubscribed email is reactivated; "
					+ "an already active email gets the already-subscribed message."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.SUBSCRIBED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.BAD_REQUEST + "",
			description = Constants.ResponseMessages.VALIDATION_FAILED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.Newsletter.SUBSCRIBE)
	public ResponseEntity<Response> subscribe(@Validated @RequestBody NewsletterRequest request) {

		String message = newsletterService.subscribe(request);

		return getOKResponseEntity(new Response(Constants.OK, message, null));
	}

//	=========================== Unsubscribe (public) ========================

	@Operation(
			summary = "Unsubscribe from Newsletter",
			description = "Unsubscribes an email. Always answers with the same message, whether or not the email "
					+ "was subscribed, so the endpoint cannot be used to check addresses."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.UNSUBSCRIBED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.BAD_REQUEST + "",
			description = Constants.ResponseMessages.VALIDATION_FAILED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.Newsletter.UNSUBSCRIBE)
	public ResponseEntity<Response> unsubscribe(@Validated @RequestBody NewsletterRequest request) {

		newsletterService.unsubscribe(request);

		return getOKResponseEntity(new Response(Constants.OK, Constants.ResponseMessages.UNSUBSCRIBED_MESSAGE, null));
	}

//	=========================== Get All Subscribers (back office) ========================

	@Operation(
			summary = "Get All Subscribers",
			description = "Paged list of newsletter subscribers, newest first. Optionally filter by active."
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
	@GetMapping(URLConstants.Newsletter.GET_ALL_SUBSCRIBERS)
	public ResponseEntity<Response> getAllSubscribers(
			@RequestParam(required = false) Boolean active,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		Response response = null;

		PageResponse<NewsletterSubscriberResponse> subscribers = newsletterService.getAllSubscribers(active,
				buildPageable(page, size, Sort.by(Sort.Direction.DESC, "subscribedAt")));

		if (subscribers.getTotalElements() > 0) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, subscribers);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, subscribers);
		}

		return getOKResponseEntity(response);
	}

}

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
import com.ca.charteredAccountant.request.ServiceCategoryRequest;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.response.ServiceCategoryResponse;
import com.ca.charteredAccountant.service.ServiceCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Service Category Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class ServiceCategoryController extends BaseController {

	private ServiceCategoryService serviceCategoryService;

//	=========================== Get All Categories (public) ========================

	@Operation(summary = "Get All Categories", description = "Service categories by sort order, for grouping the service grid and the admin select.")
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
	@GetMapping(URLConstants.ServiceCategory.GET_ALL_CATEGORIES)
	public ResponseEntity<Response> getAllCategories() {

		Response response = null;

		List<ServiceCategoryResponse> categories = serviceCategoryService.getAllCategories();

		if (categories != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, categories);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save Category ========================

	@Operation(
			summary = "Save Category",
			description = "Creates a category when categoryId is null, otherwise updates it. The slug is derived from the name when blank."
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
	@PostMapping(URLConstants.ServiceCategory.SAVE_CATEGORY)
	public ResponseEntity<Response> saveCategory(@Validated @RequestBody ServiceCategoryRequest request)
			throws CAException {

		Response response = null;

		Boolean isSaved = serviceCategoryService.saveCategory(request);

		if (Boolean.TRUE.equals(isSaved)) {
			String message = request.getCategoryId() == null ? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE;
			response = new Response(Constants.OK, message, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete Category ========================

	@Operation(summary = "Delete Category", description = "Permanently deletes a category. Refused while services still belong to it.")
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
	@ApiResponse(
			responseCode = Constants.CONFLICT + "",
			description = "Services still belong to this category",
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.ServiceCategory.DELETE_CATEGORY)
	public ResponseEntity<Response> deleteCategory(@RequestParam(name = "categoryId") Long categoryId)
			throws CAException {

		Response response = null;

		Boolean isDeleted = serviceCategoryService.deleteCategory(categoryId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

package com.ca.charteredAccountant.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ca.charteredAccountant.request.AdminUserRequest;
import com.ca.charteredAccountant.response.AdminUserResponse;
import com.ca.charteredAccountant.response.DropdownResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.AdminUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Admin User Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class AdminUserController extends BaseController {

	private AdminUserService adminUserService;

//	=========================== Get All Users (ADMIN) ========================

	@Operation(summary = "Get All Users", description = "Every back-office user, enabled and disabled, ordered by full name. ADMIN only.")
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
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(URLConstants.AdminUser.GET_ALL_USERS)
	public ResponseEntity<Response> getAllUsers() {

		Response response = null;

		List<AdminUserResponse> users = adminUserService.getAllUsers();

		if (users != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, users);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get User Dropdown ========================

	@Operation(summary = "Get User Dropdown", description = "Enabled users as id/name pairs for the \"assign enquiry to\" select box.")
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
	@GetMapping(URLConstants.AdminUser.GET_USER_DROPDOWN)
	public ResponseEntity<Response> getUserDropdown() {

		Response response = null;

		List<DropdownResponse> users = adminUserService.getUserDropdown();

		if (users != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, users);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save User (ADMIN) ========================

	@Operation(
			summary = "Save User",
			description = "Creates a user when userId is null, otherwise updates it. A password is required on create; "
					+ "on update it is changed only when sent. Admins cannot change their own role or disable themselves, "
					+ "and the last active administrator cannot be demoted or disabled. ADMIN only."
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
			description = "Username already taken, or last active administrator",
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(URLConstants.AdminUser.SAVE_USER)
	public ResponseEntity<Response> saveUser(@Validated @RequestBody AdminUserRequest request) throws CAException {

		Response response = null;

		Boolean isSaved = adminUserService.saveUser(request, getLoggedInUserDetails());

		if (Boolean.TRUE.equals(isSaved)) {
			response = new Response(Constants.OK, request.getUserId() == null
					? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete User (ADMIN) ========================

	@Operation(
			summary = "Delete User",
			description = "Disables the user (no hard delete, enquiries keep the reference). "
					+ "Admins cannot disable themselves or the last active administrator. ADMIN only."
			)
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
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(URLConstants.AdminUser.DELETE_USER)
	public ResponseEntity<Response> deleteUser(@RequestParam(name = "userId") Long userId) throws CAException {

		Response response = null;

		Boolean isDeleted = adminUserService.deleteUser(userId, getLoggedInUserDetails());

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

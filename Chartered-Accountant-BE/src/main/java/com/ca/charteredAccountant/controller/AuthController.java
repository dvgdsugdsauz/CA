package com.ca.charteredAccountant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.URLConstants;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.request.ChangePasswordRequest;
import com.ca.charteredAccountant.request.LoginRequest;
import com.ca.charteredAccountant.response.AdminUserResponse;
import com.ca.charteredAccountant.response.LoginResponse;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Authentication Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class AuthController extends BaseController {

	private AuthService authService;

//	=========================== Login (public) ========================

	@Operation(
			summary = "Login",
			description = "Checks the username and password and returns a JWT for the Authorization header "
					+ "(Bearer {token}), its lifetime in seconds and the signed-in user."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.LOGIN_SUCCESS_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.UNAUTHORIZED + "",
			description = Constants.ResponseMessages.INVALID_CREDENTIALS_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.Auth.LOGIN)
	public ResponseEntity<Response> login(@Validated @RequestBody LoginRequest request) {

		LoginResponse login = authService.login(request);

		return getOKResponseEntity(new Response(Constants.OK, Constants.ResponseMessages.LOGIN_SUCCESS_MESSAGE, login));
	}

//	=========================== Get Profile ========================

	@Operation(summary = "Get Profile", description = "The signed-in user's account details.")
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
	@GetMapping(URLConstants.Auth.GET_PROFILE)
	public ResponseEntity<Response> getProfile() throws CAException {

		Response response = null;

		AdminUserResponse profile = authService.getProfile(getLoggedInUserDetails().getUserId());

		if (profile != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, profile);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Change Password ========================

	@Operation(
			summary = "Change Password",
			description = "Changes the signed-in user's password. The new password must be 8 to 64 characters "
					+ "with at least one letter and one number, and differ from the current one."
			)
	@ApiResponse(
			responseCode = Constants.OK + "",
			description = Constants.ResponseMessages.PASSWORD_CHANGED_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@ApiResponse(
			responseCode = Constants.BAD_REQUEST + "",
			description = Constants.ResponseMessages.CURRENT_PASSWORD_WRONG_MESSAGE,
			content = @Content(schema = @Schema(implementation = Response.class))
			)
	@PostMapping(URLConstants.Auth.CHANGE_PASSWORD)
	public ResponseEntity<Response> changePassword(@Validated @RequestBody ChangePasswordRequest request)
			throws CAException {

		Response response = null;

		Boolean isChanged = authService.changePassword(request, getLoggedInUserDetails().getUserId());

		if (Boolean.TRUE.equals(isChanged)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.PASSWORD_CHANGED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

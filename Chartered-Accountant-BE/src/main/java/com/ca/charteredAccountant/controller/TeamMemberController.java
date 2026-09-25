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
import com.ca.charteredAccountant.request.TeamMemberRequest;
import com.ca.charteredAccountant.response.Response;
import com.ca.charteredAccountant.response.TeamMemberResponse;
import com.ca.charteredAccountant.service.TeamMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Team Member Module")
@RestController
@RequestMapping(URLConstants.API_BASE)
@AllArgsConstructor
public class TeamMemberController extends BaseController {

	private TeamMemberService teamMemberService;

//	=========================== Get Active Team Members (public) ========================

	@Operation(
			summary = "Get Active Team Members",
			description = "Active team members, ordered by sort order. Pass locationSlug to get only that city's members."
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
	@GetMapping(URLConstants.TeamMember.GET_ACTIVE_TEAM_MEMBERS)
	public ResponseEntity<Response> getActiveTeamMembers(@RequestParam(required = false) String locationSlug) {

		Response response = null;

		List<TeamMemberResponse> members = teamMemberService.getActiveTeamMembers(locationSlug);

		if (members != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, members);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Get All Team Members (back office) ========================

	@Operation(summary = "Get All Team Members", description = "Every team member, active and inactive, with their city.")
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
	@GetMapping(URLConstants.TeamMember.GET_ALL_TEAM_MEMBERS)
	public ResponseEntity<Response> getAllTeamMembers() {

		Response response = null;

		List<TeamMemberResponse> members = teamMemberService.getAllTeamMembers();

		if (members != null) {
			response = new Response(Constants.OK, Constants.ResponseMessages.FETCH_MESSAGE, members);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Save Team Member ========================

	@Operation(summary = "Save Team Member", description = "Creates a team member when teamMemberId is null, otherwise updates it.")
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
	@PostMapping(URLConstants.TeamMember.SAVE_TEAM_MEMBER)
	public ResponseEntity<Response> saveTeamMember(@Validated @RequestBody TeamMemberRequest request)
			throws CAException {

		Response response = null;

		Boolean isSaved = teamMemberService.saveTeamMember(request);

		if (Boolean.TRUE.equals(isSaved)) {
			response = new Response(Constants.OK, request.getTeamMemberId() == null
					? Constants.ResponseMessages.SAVE_MESSAGE
					: Constants.ResponseMessages.UPDATE_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

//	=========================== Delete Team Member ========================

	@Operation(summary = "Delete Team Member", description = "Soft delete: marks the team member inactive.")
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
	@PostMapping(URLConstants.TeamMember.DELETE_TEAM_MEMBER)
	public ResponseEntity<Response> deleteTeamMember(@RequestParam(name = "teamMemberId") Long teamMemberId) {

		Response response = null;

		Boolean isDeleted = teamMemberService.deleteTeamMember(teamMemberId);

		if (Boolean.TRUE.equals(isDeleted)) {
			response = new Response(Constants.OK, Constants.ResponseMessages.DELETED_MESSAGE, null);
		} else {
			response = new Response(Constants.NO_CONTENT, Constants.ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}

		return getOKResponseEntity(response);
	}

}

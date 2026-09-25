package com.ca.charteredAccountant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.OfficeLocationEntity;
import com.ca.charteredAccountant.dao.model.TeamMemberEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.OfficeLocationRepository;
import com.ca.charteredAccountant.repository.TeamMemberRepository;
import com.ca.charteredAccountant.request.TeamMemberRequest;
import com.ca.charteredAccountant.response.TeamMemberResponse;
import com.ca.charteredAccountant.service.TeamMemberService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

	private static final String INVALID_CITY_MESSAGE = "Select a valid city";

	private TeamMemberRepository teamMemberRepository;
	private OfficeLocationRepository officeLocationRepository;

	// ========================= Public (website) =========================

	@Override
	@Transactional(readOnly = true)
	public List<TeamMemberResponse> getActiveTeamMembers(String locationSlug) {
		List<TeamMemberEntity> members = teamMemberRepository.findActive(CommonUtil.trimToNull(locationSlug));
		return members.isEmpty() ? null : members.stream().map(this::mapEntityToResponse).toList();
	}

	// ========================= List (back office) =========================

	@Override
	@Transactional(readOnly = true)
	public List<TeamMemberResponse> getAllTeamMembers() {
		List<TeamMemberEntity> members = teamMemberRepository.findAllByOrderBySortOrderAscIdAsc();
		return members.isEmpty() ? null : members.stream().map(this::mapEntityToResponse).toList();
	}

	// ========================= Save (create / update) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveTeamMember(TeamMemberRequest request) throws CAException {

		TeamMemberEntity entity;
		if (request.getTeamMemberId() == null) {
			entity = new TeamMemberEntity();
		} else {
			entity = teamMemberRepository.findById(request.getTeamMemberId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		OfficeLocationEntity location = null;
		if (request.getLocationId() != null) {
			location = officeLocationRepository.findById(request.getLocationId())
					.orElseThrow(() -> CAException.badRequest(INVALID_CITY_MESSAGE));
		}

		entity.setFullName(request.getFullName().trim());
		entity.setDesignation(request.getDesignation().trim());
		entity.setQualifications(CommonUtil.trimToNull(request.getQualifications()));
		entity.setBio(CommonUtil.trimToNull(request.getBio()));
		entity.setPhotoUrl(CommonUtil.trimToNull(request.getPhotoUrl()));
		entity.setLocation(location);
		entity.setActive(request.getActive() != null ? request.getActive()
				: (entity.getActive() != null ? entity.getActive() : Constants.ACTIVE));
		entity.setSortOrder(request.getSortOrder() != null ? request.getSortOrder()
				: CommonUtil.orZero(entity.getSortOrder()));

		teamMemberRepository.save(entity);
		return true;
	}

	// ========================= Delete (soft) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteTeamMember(Long teamMemberId) {
		TeamMemberEntity entity = teamMemberRepository.findById(teamMemberId).orElse(null);
		if (entity == null) {
			return false;
		}
		entity.setActive(Constants.INACTIVE);
		teamMemberRepository.save(entity);
		return true;
	}

	// ========================= Mapping =========================

	private TeamMemberResponse mapEntityToResponse(TeamMemberEntity e) {
		TeamMemberResponse r = new TeamMemberResponse();
		r.setTeamMemberId(e.getId());
		r.setFullName(e.getFullName());
		r.setDesignation(e.getDesignation());
		r.setQualifications(e.getQualifications());
		r.setBio(e.getBio());
		r.setPhotoUrl(e.getPhotoUrl());
		r.setLocationId(e.getLocation() != null ? e.getLocation().getId() : null);
		r.setCity(e.getLocation() != null ? e.getLocation().getCity() : null);
		r.setActive(e.getActive());
		r.setSortOrder(e.getSortOrder());
		return r;
	}

}

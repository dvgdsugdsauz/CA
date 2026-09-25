package com.ca.charteredAccountant.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.common.enums.EnquiryStatus;
import com.ca.charteredAccountant.dao.model.AdminUserEntity;
import com.ca.charteredAccountant.dao.model.EnquiryEntity;
import com.ca.charteredAccountant.dao.model.LoggedInUserDetails;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.AdminUserRepository;
import com.ca.charteredAccountant.repository.EnquiryRepository;
import com.ca.charteredAccountant.repository.FirmServiceRepository;
import com.ca.charteredAccountant.repository.OfficeLocationRepository;
import com.ca.charteredAccountant.request.EnquiryRequest;
import com.ca.charteredAccountant.request.EnquiryUpdateRequest;
import com.ca.charteredAccountant.response.EnquiryResponse;
import com.ca.charteredAccountant.response.EnquiryStatusSummaryResponse;
import com.ca.charteredAccountant.response.EnquirySubmitResponse;
import com.ca.charteredAccountant.response.PageResponse;
import com.ca.charteredAccountant.service.EnquiryService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class EnquiryServiceImpl implements EnquiryService {

	private EnquiryRepository enquiryRepository;
	private FirmServiceRepository firmServiceRepository;
	private OfficeLocationRepository officeLocationRepository;
	private AdminUserRepository adminUserRepository;

	// ========================= Submit (public website) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public EnquirySubmitResponse submitEnquiry(EnquiryRequest request) throws CAException {

		EnquiryEntity entity = EnquiryEntity.builder()
				.fullName(request.getFullName().trim())
				.email(CommonUtil.normalizeEmail(request.getEmail()))
				.phone(request.getPhone().trim())
				.companyName(CommonUtil.trimToNull(request.getCompanyName()))
				.message(CommonUtil.trimToNull(request.getMessage()))
				.sourcePage(CommonUtil.trimToNull(request.getSourcePage()))
				.status(EnquiryStatus.NEW)
				.build();

		if (request.getServiceId() != null) {
			firmServiceRepository.findById(request.getServiceId())
					.filter(s -> Boolean.TRUE.equals(s.getActive()))
					.ifPresent(entity::setService);
		}
		if (request.getLocationId() != null) {
			officeLocationRepository.findById(request.getLocationId())
					.filter(l -> Boolean.TRUE.equals(l.getActive()))
					.ifPresent(entity::setLocation);
		}

		EnquiryEntity saved = enquiryRepository.save(entity);
		saved.setReferenceNo(buildReferenceNo(saved.getId()));
		enquiryRepository.save(saved);

		log.info("Enquiry {} received from {}", saved.getReferenceNo(), saved.getSourcePage());
		return new EnquirySubmitResponse(saved.getReferenceNo());
	}

	private String buildReferenceNo(Long id) {
		return String.format(Constants.ENQUIRY_REF_FORMAT, Constants.ENQUIRY_REF_PREFIX, LocalDate.now().getYear(), id);
	}

	// ========================= List (back office) =========================

	@Override
	@Transactional(readOnly = true)
	public PageResponse<EnquiryResponse> getAllEnquiries(EnquiryStatus status, String search, Pageable pageable) {
		return PageResponse.of(
				enquiryRepository.searchEnquiries(status, CommonUtil.trimToNull(search), pageable),
				this::mapEntityToResponse);
	}

	// ========================= Status Counts =========================

	@Override
	@Transactional(readOnly = true)
	public EnquiryStatusSummaryResponse getStatusSummary() {

		Map<EnquiryStatus, Long> counts = new EnumMap<>(EnquiryStatus.class);
		for (Object[] row : enquiryRepository.countGroupByStatus()) {
			counts.put((EnquiryStatus) row[0], (Long) row[1]);
		}

		long total = 0;
		List<EnquiryStatusSummaryResponse.StatusCount> statuses = new ArrayList<>();
		for (EnquiryStatus status : EnquiryStatus.values()) {
			long count = counts.getOrDefault(status, 0L);
			total += count;
			statuses.add(new EnquiryStatusSummaryResponse.StatusCount(status, status.getLabel(), count));
		}
		return new EnquiryStatusSummaryResponse(total, statuses);
	}

	// ========================= Get Single =========================

	@Override
	@Transactional(readOnly = true)
	public EnquiryResponse getEnquiry(Long enquiryId) {
		return enquiryRepository.findWithDetailsById(enquiryId).map(this::mapEntityToResponse).orElse(null);
	}

	// ========================= Update (status, notes, owner) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateEnquiry(EnquiryUpdateRequest request, LoggedInUserDetails loggedInUserDetails)
			throws CAException {

		EnquiryEntity entity = enquiryRepository.findById(request.getEnquiryId()).orElse(null);
		if (entity == null) {
			return null;
		}

		AdminUserEntity assignee = null;
		if (request.getAssignedToId() != null) {
			assignee = adminUserRepository.findById(request.getAssignedToId())
					.filter(u -> Boolean.TRUE.equals(u.getEnabled()))
					.orElseThrow(() -> CAException.badRequest("Select an active team member to assign"));
		}

		if (entity.getStatus() != request.getStatus()) {
			log.info("Enquiry {} status {} -> {} by {}", entity.getReferenceNo(), entity.getStatus(),
					request.getStatus(), loggedInUserDetails.getUsername());
		}

		entity.setStatus(request.getStatus());
		entity.setInternalNotes(CommonUtil.trimToNull(request.getInternalNotes()));
		entity.setAssignedTo(assignee);
		enquiryRepository.save(entity);
		return true;
	}

	// ========================= Mapping =========================

	private EnquiryResponse mapEntityToResponse(EnquiryEntity e) {
		EnquiryResponse r = new EnquiryResponse();
		r.setEnquiryId(e.getId());
		r.setReferenceNo(e.getReferenceNo());
		r.setFullName(e.getFullName());
		r.setEmail(e.getEmail());
		r.setPhone(e.getPhone());
		r.setCompanyName(e.getCompanyName());
		r.setServiceId(e.getService() != null ? e.getService().getId() : null);
		r.setServiceTitle(e.getService() != null ? e.getService().getTitle() : null);
		r.setLocationId(e.getLocation() != null ? e.getLocation().getId() : null);
		r.setCity(e.getLocation() != null ? e.getLocation().getCity() : null);
		r.setMessage(e.getMessage());
		r.setSourcePage(e.getSourcePage());
		r.setStatus(e.getStatus());
		r.setStatusLabel(e.getStatus() != null ? e.getStatus().getLabel() : null);
		r.setInternalNotes(e.getInternalNotes());
		r.setAssignedToId(e.getAssignedTo() != null ? e.getAssignedTo().getId() : null);
		r.setAssignedToName(e.getAssignedTo() != null ? e.getAssignedTo().getFullName() : null);
		r.setCreatedAt(e.getCreatedAt());
		r.setUpdatedAt(e.getUpdatedAt());
		return r;
	}

}

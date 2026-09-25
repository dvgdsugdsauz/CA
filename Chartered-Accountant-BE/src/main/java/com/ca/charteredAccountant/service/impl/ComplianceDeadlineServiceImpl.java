package com.ca.charteredAccountant.service.impl;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.ComplianceDeadlineEntity;
import com.ca.charteredAccountant.repository.ComplianceDeadlineRepository;
import com.ca.charteredAccountant.request.ComplianceDeadlineRequest;
import com.ca.charteredAccountant.response.ComplianceDeadlineResponse;
import com.ca.charteredAccountant.service.ComplianceDeadlineService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ComplianceDeadlineServiceImpl implements ComplianceDeadlineService {

	private static final int DEFAULT_LIMIT = 5;
	private static final int MAX_LIMIT = 20;
	private static final int DUE_SOON_DAYS = 7;

	private ComplianceDeadlineRepository complianceDeadlineRepository;

	// ========================= Upcoming (home page ledger) =========================

	@Override
	@Transactional(readOnly = true)
	public List<ComplianceDeadlineResponse> getUpcomingDeadlines(Integer limit) {
		int size = limit == null ? DEFAULT_LIMIT : Math.max(1, Math.min(limit, MAX_LIMIT));
		LocalDate today = LocalDate.now();
		List<ComplianceDeadlineEntity> deadlines = complianceDeadlineRepository
				.findAllByActiveAndDueDateGreaterThanEqualOrderByDueDateAscIdAsc(Constants.ACTIVE, today,
						PageRequest.of(0, size));
		return deadlines.isEmpty() ? null : deadlines.stream().map(d -> mapEntityToResponse(d, today)).toList();
	}

	// ========================= List (back office) =========================

	@Override
	@Transactional(readOnly = true)
	public List<ComplianceDeadlineResponse> getAllDeadlines() {
		LocalDate today = LocalDate.now();
		List<ComplianceDeadlineEntity> deadlines = complianceDeadlineRepository.findAllByOrderByDueDateDescIdDesc();
		return deadlines.isEmpty() ? null : deadlines.stream().map(d -> mapEntityToResponse(d, today)).toList();
	}

	// ========================= Save (create / update) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveDeadline(ComplianceDeadlineRequest request) {

		ComplianceDeadlineEntity entity;
		if (request.getDeadlineId() == null) {
			entity = new ComplianceDeadlineEntity();
		} else {
			entity = complianceDeadlineRepository.findById(request.getDeadlineId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		entity.setDueDate(request.getDueDate());
		entity.setTitle(request.getTitle().trim());
		entity.setLaw(request.getLaw());
		entity.setAppliesTo(CommonUtil.trimToNull(request.getAppliesTo()));
		entity.setActive(request.getActive() != null ? request.getActive()
				: (entity.getActive() != null ? entity.getActive() : Constants.ACTIVE));

		complianceDeadlineRepository.save(entity);
		return true;
	}

	// ========================= Delete (soft) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteDeadline(Long deadlineId) {
		ComplianceDeadlineEntity entity = complianceDeadlineRepository.findById(deadlineId).orElse(null);
		if (entity == null) {
			return false;
		}
		entity.setActive(Constants.INACTIVE);
		complianceDeadlineRepository.save(entity);
		return true;
	}

	// ========================= Mapping =========================

	private ComplianceDeadlineResponse mapEntityToResponse(ComplianceDeadlineEntity e, LocalDate today) {
		ComplianceDeadlineResponse r = new ComplianceDeadlineResponse();
		LocalDate dueDate = e.getDueDate();
		r.setDeadlineId(e.getId());
		r.setDueDate(dueDate);
		if (dueDate != null) {
			r.setDay(String.format("%02d", dueDate.getDayOfMonth()));
			r.setMonth(dueDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase(Locale.ENGLISH));
			r.setDueSoon(!dueDate.isBefore(today) && !dueDate.isAfter(today.plusDays(DUE_SOON_DAYS)));
		} else {
			r.setDueSoon(false);
		}
		r.setTitle(e.getTitle());
		r.setLaw(e.getLaw());
		r.setLawLabel(e.getLaw() != null ? e.getLaw().getLabel() : null);
		r.setAppliesTo(e.getAppliesTo());
		r.setActive(e.getActive());
		return r;
	}

}

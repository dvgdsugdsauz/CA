package com.ca.charteredAccountant.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.dao.model.JobOpeningEntity;
import com.ca.charteredAccountant.dao.model.OfficeLocationEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.JobOpeningRepository;
import com.ca.charteredAccountant.repository.OfficeLocationRepository;
import com.ca.charteredAccountant.request.JobOpeningRequest;
import com.ca.charteredAccountant.response.JobOpeningResponse;
import com.ca.charteredAccountant.service.JobOpeningService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class JobOpeningServiceImpl implements JobOpeningService {

	private static final String SLUG_TAKEN_MESSAGE = "This URL slug is already used by another job opening";
	private static final String INVALID_SLUG_MESSAGE = "Enter a valid URL slug";
	private static final String INVALID_LOCATION_MESSAGE = "Select a valid city";

	private JobOpeningRepository jobOpeningRepository;
	private OfficeLocationRepository officeLocationRepository;

	// ========================= Public (careers page) =========================

	@Override
	@Transactional(readOnly = true)
	public List<JobOpeningResponse> getActiveJobOpenings() {
		List<JobOpeningResponse> openings = jobOpeningRepository.findOpenJobs(LocalDate.now()).stream()
				.map(this::mapEntityToResponse).toList();
		return openings.isEmpty() ? null : openings;
	}

	@Override
	@Transactional(readOnly = true)
	public JobOpeningResponse getJobOpeningBySlug(String slug) {
		return jobOpeningRepository.findOpenBySlug(slug, LocalDate.now()).map(this::mapEntityToResponse).orElse(null);
	}

	// ========================= Back office =========================

	@Override
	@Transactional(readOnly = true)
	public List<JobOpeningResponse> getAllJobOpenings() {
		List<JobOpeningResponse> openings = jobOpeningRepository.findAllByOrderByPostedAtDesc().stream()
				.map(this::mapEntityToResponse).toList();
		return openings.isEmpty() ? null : openings;
	}

	@Override
	@Transactional(readOnly = true)
	public JobOpeningResponse getJobOpening(Long jobId) {
		return jobOpeningRepository.findWithLocationById(jobId).map(this::mapEntityToResponse).orElse(null);
	}

	// ========================= Save (create or update) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveJobOpening(JobOpeningRequest request) throws CAException {

		boolean isCreate = request.getJobId() == null;

		JobOpeningEntity entity;
		if (isCreate) {
			entity = new JobOpeningEntity();
		} else {
			entity = jobOpeningRepository.findById(request.getJobId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		String slug = CommonUtil.resolveSlug(request.getSlug(), request.getTitle());
		if (slug == null) {
			throw CAException.badRequest(INVALID_SLUG_MESSAGE);
		}
		boolean slugTaken = isCreate ? jobOpeningRepository.existsBySlug(slug)
				: jobOpeningRepository.existsBySlugAndIdNot(slug, request.getJobId());
		if (slugTaken) {
			throw CAException.conflict(SLUG_TAKEN_MESSAGE);
		}

		OfficeLocationEntity location = null;
		if (request.getLocationId() != null) {
			location = officeLocationRepository.findById(request.getLocationId())
					.orElseThrow(() -> CAException.badRequest(INVALID_LOCATION_MESSAGE));
		}

		entity.setTitle(request.getTitle().trim());
		entity.setSlug(slug);
		entity.setLocation(location);
		entity.setDepartment(CommonUtil.trimToNull(request.getDepartment()));
		entity.setExperienceRange(CommonUtil.trimToNull(request.getExperienceRange()));
		entity.setEmploymentType(request.getEmploymentType());
		entity.setDescription(CommonUtil.trimToNull(request.getDescription()));
		entity.setClosesOn(request.getClosesOn());

		if (isCreate) {
			entity.setActive(request.getActive() == null ? Boolean.TRUE : request.getActive());
			entity.setPostedAt(LocalDateTime.now());
		} else if (request.getActive() != null) {
			entity.setActive(request.getActive());
		}

		jobOpeningRepository.save(entity);
		return true;
	}

	// ========================= Delete (soft) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteJobOpening(Long jobId) {
		JobOpeningEntity entity = jobOpeningRepository.findById(jobId).orElse(null);
		if (entity == null) {
			return false;
		}
		entity.setActive(Boolean.FALSE);
		jobOpeningRepository.save(entity);
		return true;
	}

	// ========================= Mapping =========================

	private JobOpeningResponse mapEntityToResponse(JobOpeningEntity e) {
		JobOpeningResponse r = new JobOpeningResponse();
		r.setJobId(e.getId());
		r.setTitle(e.getTitle());
		r.setSlug(e.getSlug());
		r.setLocationId(e.getLocation() != null ? e.getLocation().getId() : null);
		r.setCity(e.getLocation() != null ? e.getLocation().getCity() : null);
		r.setDepartment(e.getDepartment());
		r.setExperienceRange(e.getExperienceRange());
		r.setEmploymentType(e.getEmploymentType());
		r.setEmploymentTypeLabel(e.getEmploymentType() != null ? e.getEmploymentType().getLabel() : null);
		r.setDescription(e.getDescription());
		r.setActive(e.getActive());
		r.setPostedAt(e.getPostedAt());
		r.setClosesOn(e.getClosesOn());
		return r;
	}

}

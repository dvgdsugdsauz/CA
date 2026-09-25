package com.ca.charteredAccountant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.OfficeLocationEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.OfficeLocationRepository;
import com.ca.charteredAccountant.request.OfficeLocationRequest;
import com.ca.charteredAccountant.response.OfficeLocationResponse;
import com.ca.charteredAccountant.service.OfficeLocationService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class OfficeLocationServiceImpl implements OfficeLocationService {

	private static final int AREAS_SERVED_MAX_LENGTH = 300;
	private static final String INVALID_SLUG_MESSAGE = "Enter a valid URL slug, e.g. hyderabad";
	private static final String DUPLICATE_SLUG_MESSAGE = "This URL slug is already used by another location";
	private static final String AREAS_TOO_LONG_MESSAGE = "Areas served can be up to 300 characters in total";

	private OfficeLocationRepository officeLocationRepository;

	// ========================= Public =========================

	@Override
	@Transactional(readOnly = true)
	public List<OfficeLocationResponse> getAllActiveLocations() {
		return toResponseList(officeLocationRepository.findAllByActiveOrderBySortOrderAsc(Constants.ACTIVE));
	}

	@Override
	@Transactional(readOnly = true)
	public OfficeLocationResponse getLocationBySlug(String slug) {
		return officeLocationRepository.findBySlugAndActive(slug, Constants.ACTIVE)
				.map(this::mapEntityToResponse).orElse(null);
	}

	// ========================= Back Office =========================

	@Override
	@Transactional(readOnly = true)
	public List<OfficeLocationResponse> getAllLocations() {
		return toResponseList(officeLocationRepository.findAllByOrderBySortOrderAsc());
	}

	@Override
	@Transactional(readOnly = true)
	public OfficeLocationResponse getLocation(Long locationId) {
		return officeLocationRepository.findById(locationId).map(this::mapEntityToResponse).orElse(null);
	}

	// ========================= Save =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveLocation(OfficeLocationRequest request) throws CAException {

		boolean isNew = request.getLocationId() == null;
		OfficeLocationEntity entity;
		if (isNew) {
			entity = new OfficeLocationEntity();
		} else {
			entity = officeLocationRepository.findById(request.getLocationId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		String slug = CommonUtil.resolveSlug(request.getSlug(), request.getCity());
		if (slug == null) {
			throw CAException.badRequest(INVALID_SLUG_MESSAGE);
		}
		boolean slugTaken = isNew ? officeLocationRepository.existsBySlug(slug)
				: officeLocationRepository.existsBySlugAndIdNot(slug, entity.getId());
		if (slugTaken) {
			throw CAException.conflict(DUPLICATE_SLUG_MESSAGE);
		}

		String areasServed = CommonUtil.joinCsv(request.getAreasServed());
		if (areasServed != null && areasServed.length() > AREAS_SERVED_MAX_LENGTH) {
			throw CAException.badRequest(AREAS_TOO_LONG_MESSAGE);
		}

		entity.setCity(request.getCity().trim());
		entity.setSlug(slug);
		entity.setHeroHeading(CommonUtil.trimToNull(request.getHeroHeading()));
		entity.setIntro(CommonUtil.trimToNull(request.getIntro()));
		entity.setAreasServed(CommonUtil.trimToNull(areasServed));
		entity.setAddressLine(request.getAddressLine().trim());
		entity.setPhone(CommonUtil.trimToNull(request.getPhone()));
		entity.setEmail(CommonUtil.normalizeEmail(request.getEmail()));
		entity.setOfficeHours(CommonUtil.trimToNull(request.getOfficeHours()));
		entity.setMapUrl(CommonUtil.trimToNull(request.getMapUrl()));
		entity.setMetaTitle(CommonUtil.trimToNull(request.getMetaTitle()));
		entity.setMetaDescription(CommonUtil.trimToNull(request.getMetaDescription()));

		if (request.getHeadOffice() != null || isNew) {
			entity.setHeadOffice(Boolean.TRUE.equals(request.getHeadOffice()));
		}
		if (request.getActive() != null || isNew) {
			entity.setActive(request.getActive() == null ? Constants.ACTIVE : request.getActive());
		}
		if (request.getSortOrder() != null || isNew) {
			entity.setSortOrder(CommonUtil.orZero(request.getSortOrder()));
		}

		OfficeLocationEntity saved = officeLocationRepository.save(entity);

		if (Boolean.TRUE.equals(saved.getHeadOffice())) {
			int cleared = officeLocationRepository.clearHeadOfficeExcept(saved.getId());
			if (cleared > 0) {
				log.info("Head office moved to {}", saved.getCity());
			}
		}
		return true;
	}

	// ========================= Delete (soft) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteLocation(Long locationId) {
		OfficeLocationEntity entity = officeLocationRepository.findById(locationId).orElse(null);
		if (entity == null) {
			return false;
		}
		entity.setActive(Constants.INACTIVE);
		officeLocationRepository.save(entity);
		return true;
	}

	// ========================= Mapping =========================

	private List<OfficeLocationResponse> toResponseList(List<OfficeLocationEntity> entities) {
		return entities.isEmpty() ? null : entities.stream().map(this::mapEntityToResponse).toList();
	}

	private OfficeLocationResponse mapEntityToResponse(OfficeLocationEntity e) {
		OfficeLocationResponse r = new OfficeLocationResponse();
		r.setLocationId(e.getId());
		r.setCity(e.getCity());
		r.setSlug(e.getSlug());
		r.setHeroHeading(e.getHeroHeading());
		r.setIntro(e.getIntro());
		r.setAreasServed(CommonUtil.splitCsv(e.getAreasServed()));
		r.setAddressLine(e.getAddressLine());
		r.setPhone(e.getPhone());
		r.setEmail(e.getEmail());
		r.setOfficeHours(e.getOfficeHours());
		r.setMapUrl(e.getMapUrl());
		r.setMetaTitle(e.getMetaTitle());
		r.setMetaDescription(e.getMetaDescription());
		r.setHeadOffice(e.getHeadOffice());
		r.setActive(e.getActive());
		r.setSortOrder(e.getSortOrder());
		return r;
	}

}

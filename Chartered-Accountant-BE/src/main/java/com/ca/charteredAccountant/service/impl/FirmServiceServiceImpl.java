package com.ca.charteredAccountant.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.FirmServiceEntity;
import com.ca.charteredAccountant.dao.model.ServiceCategoryEntity;
import com.ca.charteredAccountant.dao.model.ServiceHighlightEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.FirmServiceRepository;
import com.ca.charteredAccountant.repository.ServiceCategoryRepository;
import com.ca.charteredAccountant.request.FirmServiceRequest;
import com.ca.charteredAccountant.response.DropdownResponse;
import com.ca.charteredAccountant.response.FirmServiceResponse;
import com.ca.charteredAccountant.response.FirmServiceSummaryResponse;
import com.ca.charteredAccountant.service.FirmServiceService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FirmServiceServiceImpl implements FirmServiceService {

	private static final int RELATED_SERVICES_LIMIT = 4;
	private static final String INVALID_SLUG_MESSAGE = "Enter a valid URL slug, e.g. gst-registration";
	private static final String DUPLICATE_SLUG_MESSAGE = "This URL slug is already used by another service";
	private static final String INVALID_CATEGORY_MESSAGE = "Select a valid category";

	private FirmServiceRepository firmServiceRepository;
	private ServiceCategoryRepository serviceCategoryRepository;

	// ========================= Public =========================

	@Override
	@Transactional(readOnly = true)
	public List<FirmServiceSummaryResponse> getAllActiveServices() {
		return toSummaryList(firmServiceRepository.findAllByActiveOrderBySortOrderAsc(Constants.ACTIVE));
	}

	@Override
	@Transactional(readOnly = true)
	public FirmServiceResponse getServiceBySlug(String slug) {
		FirmServiceEntity entity = firmServiceRepository.findBySlugAndActive(slug, Constants.ACTIVE).orElse(null);
		if (entity == null) {
			return null;
		}
		FirmServiceResponse response = mapEntityToResponse(entity);
		response.setRelatedServices(findRelatedServices(entity));
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DropdownResponse> getServiceDropdown() {
		List<FirmServiceEntity> entities = firmServiceRepository.findAllByActiveOrderBySortOrderAsc(Constants.ACTIVE);
		return entities.isEmpty() ? null
				: entities.stream().map(e -> new DropdownResponse(e.getId(), e.getTitle())).toList();
	}

	/** Up to 4 other active services: same category first, then the rest, each by sortOrder. */
	private List<FirmServiceSummaryResponse> findRelatedServices(FirmServiceEntity entity) {
		Long categoryId = entity.getCategory() != null ? entity.getCategory().getId() : null;

		List<FirmServiceEntity> sameCategory = new ArrayList<>();
		List<FirmServiceEntity> others = new ArrayList<>();
		for (FirmServiceEntity candidate : firmServiceRepository.findAllByActiveOrderBySortOrderAsc(Constants.ACTIVE)) {
			if (candidate.getId().equals(entity.getId())) {
				continue;
			}
			Long candidateCategoryId = candidate.getCategory() != null ? candidate.getCategory().getId() : null;
			if (categoryId != null && Objects.equals(categoryId, candidateCategoryId)) {
				sameCategory.add(candidate);
			} else {
				others.add(candidate);
			}
		}
		sameCategory.addAll(others);
		return sameCategory.stream().limit(RELATED_SERVICES_LIMIT).map(this::mapEntityToSummary).toList();
	}

	// ========================= Back Office =========================

	@Override
	@Transactional(readOnly = true)
	public List<FirmServiceSummaryResponse> getAllServices() {
		return toSummaryList(firmServiceRepository.findAllByOrderBySortOrderAsc());
	}

	@Override
	@Transactional(readOnly = true)
	public FirmServiceResponse getService(Long serviceId) {
		return firmServiceRepository.findWithDetailsById(serviceId).map(e -> {
			FirmServiceResponse response = mapEntityToResponse(e);
			response.setRelatedServices(List.of());
			return response;
		}).orElse(null);
	}

	// ========================= Save =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveService(FirmServiceRequest request) throws CAException {

		boolean isNew = request.getServiceId() == null;
		FirmServiceEntity entity;
		if (isNew) {
			entity = new FirmServiceEntity();
			entity.setHighlights(new ArrayList<>());
		} else {
			entity = firmServiceRepository.findWithDetailsById(request.getServiceId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		ServiceCategoryEntity category = null;
		if (request.getCategoryId() != null) {
			category = serviceCategoryRepository.findById(request.getCategoryId())
					.orElseThrow(() -> CAException.badRequest(INVALID_CATEGORY_MESSAGE));
		}

		String slug = CommonUtil.resolveSlug(request.getSlug(), request.getTitle());
		if (slug == null) {
			throw CAException.badRequest(INVALID_SLUG_MESSAGE);
		}
		boolean slugTaken = isNew ? firmServiceRepository.existsBySlug(slug)
				: firmServiceRepository.existsBySlugAndIdNot(slug, entity.getId());
		if (slugTaken) {
			throw CAException.conflict(DUPLICATE_SLUG_MESSAGE);
		}

		entity.setCategory(category);
		entity.setTitle(request.getTitle().trim());
		entity.setSlug(slug);
		entity.setSummary(request.getSummary().trim());
		entity.setBody(CommonUtil.trimToNull(request.getBody()));
		entity.setIcon(CommonUtil.trimToNull(request.getIcon()));
		entity.setMetaTitle(CommonUtil.trimToNull(request.getMetaTitle()));
		entity.setMetaDescription(CommonUtil.trimToNull(request.getMetaDescription()));

		if (request.getFeatured() != null || isNew) {
			entity.setFeatured(Boolean.TRUE.equals(request.getFeatured()));
		}
		if (request.getActive() != null || isNew) {
			entity.setActive(request.getActive() == null ? Constants.ACTIVE : request.getActive());
		}
		if (request.getSortOrder() != null || isNew) {
			entity.setSortOrder(CommonUtil.orZero(request.getSortOrder()));
		}

		replaceHighlights(entity, request.getHighlights());

		firmServiceRepository.save(entity);
		return true;
	}

	/** Clears the list in place so orphanRemoval deletes the old rows. */
	private void replaceHighlights(FirmServiceEntity entity, List<String> highlights) {
		entity.getHighlights().clear();
		if (highlights == null) {
			return;
		}
		int sortOrder = 1;
		for (String text : highlights) {
			if (!StringUtils.hasText(text)) {
				continue;
			}
			entity.getHighlights().add(ServiceHighlightEntity.builder()
					.service(entity)
					.text(text.trim())
					.sortOrder(sortOrder++)
					.build());
		}
	}

	// ========================= Delete (soft) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteService(Long serviceId) {
		FirmServiceEntity entity = firmServiceRepository.findById(serviceId).orElse(null);
		if (entity == null) {
			return false;
		}
		entity.setActive(Constants.INACTIVE);
		firmServiceRepository.save(entity);
		return true;
	}

	// ========================= Mapping =========================

	private List<FirmServiceSummaryResponse> toSummaryList(List<FirmServiceEntity> entities) {
		return entities.isEmpty() ? null : entities.stream().map(this::mapEntityToSummary).toList();
	}

	private FirmServiceSummaryResponse mapEntityToSummary(FirmServiceEntity e) {
		FirmServiceSummaryResponse r = new FirmServiceSummaryResponse();
		fillSummary(r, e);
		return r;
	}

	private FirmServiceResponse mapEntityToResponse(FirmServiceEntity e) {
		FirmServiceResponse r = new FirmServiceResponse();
		fillSummary(r, e);
		r.setBody(e.getBody());
		r.setMetaTitle(e.getMetaTitle());
		r.setMetaDescription(e.getMetaDescription());
		r.setHighlights(e.getHighlights().stream()
				.sorted((a, b) -> Integer.compare(CommonUtil.orZero(a.getSortOrder()), CommonUtil.orZero(b.getSortOrder())))
				.map(ServiceHighlightEntity::getText)
				.toList());
		return r;
	}

	private void fillSummary(FirmServiceSummaryResponse r, FirmServiceEntity e) {
		ServiceCategoryEntity category = e.getCategory();
		r.setServiceId(e.getId());
		r.setTitle(e.getTitle());
		r.setSlug(e.getSlug());
		r.setSummary(e.getSummary());
		r.setIcon(e.getIcon());
		r.setCategoryId(category != null ? category.getId() : null);
		r.setCategoryName(category != null ? category.getName() : null);
		r.setCategorySlug(category != null ? category.getSlug() : null);
		r.setFeatured(e.getFeatured());
		r.setActive(e.getActive());
		r.setSortOrder(e.getSortOrder());
	}

}

package com.ca.charteredAccountant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.dao.model.ServiceCategoryEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.FirmServiceRepository;
import com.ca.charteredAccountant.repository.ServiceCategoryRepository;
import com.ca.charteredAccountant.request.ServiceCategoryRequest;
import com.ca.charteredAccountant.response.ServiceCategoryResponse;
import com.ca.charteredAccountant.service.ServiceCategoryService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

	private static final String INVALID_SLUG_MESSAGE = "Enter a valid URL slug, e.g. tax-services";
	private static final String DUPLICATE_SLUG_MESSAGE = "This URL slug is already used by another category";
	private static final String CATEGORY_IN_USE_MESSAGE = "Move or delete the services in this category first";

	private ServiceCategoryRepository serviceCategoryRepository;
	private FirmServiceRepository firmServiceRepository;

	// ========================= List =========================

	@Override
	@Transactional(readOnly = true)
	public List<ServiceCategoryResponse> getAllCategories() {
		List<ServiceCategoryEntity> entities = serviceCategoryRepository.findAllByOrderBySortOrderAsc();
		return entities.isEmpty() ? null : entities.stream().map(this::mapEntityToResponse).toList();
	}

	// ========================= Save =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveCategory(ServiceCategoryRequest request) throws CAException {

		boolean isNew = request.getCategoryId() == null;
		ServiceCategoryEntity entity;
		if (isNew) {
			entity = new ServiceCategoryEntity();
		} else {
			entity = serviceCategoryRepository.findById(request.getCategoryId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		String slug = CommonUtil.resolveSlug(request.getSlug(), request.getName());
		if (slug == null) {
			throw CAException.badRequest(INVALID_SLUG_MESSAGE);
		}
		boolean slugTaken = isNew ? serviceCategoryRepository.existsBySlug(slug)
				: serviceCategoryRepository.existsBySlugAndIdNot(slug, entity.getId());
		if (slugTaken) {
			throw CAException.conflict(DUPLICATE_SLUG_MESSAGE);
		}

		entity.setName(request.getName().trim());
		entity.setSlug(slug);
		if (request.getSortOrder() != null || isNew) {
			entity.setSortOrder(CommonUtil.orZero(request.getSortOrder()));
		}
		serviceCategoryRepository.save(entity);
		return true;
	}

	// ========================= Delete (hard) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteCategory(Long categoryId) throws CAException {
		if (!serviceCategoryRepository.existsById(categoryId)) {
			return false;
		}
		if (firmServiceRepository.existsByCategoryId(categoryId)) {
			throw CAException.conflict(CATEGORY_IN_USE_MESSAGE);
		}
		serviceCategoryRepository.deleteById(categoryId);
		log.info("Service category {} deleted", categoryId);
		return true;
	}

	// ========================= Mapping =========================

	private ServiceCategoryResponse mapEntityToResponse(ServiceCategoryEntity e) {
		ServiceCategoryResponse r = new ServiceCategoryResponse();
		r.setCategoryId(e.getId());
		r.setName(e.getName());
		r.setSlug(e.getSlug());
		r.setSortOrder(e.getSortOrder());
		return r;
	}

}

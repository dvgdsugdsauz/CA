package com.ca.charteredAccountant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.dao.model.IndustryEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.IndustryRepository;
import com.ca.charteredAccountant.request.IndustryRequest;
import com.ca.charteredAccountant.response.IndustryResponse;
import com.ca.charteredAccountant.service.IndustryService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class IndustryServiceImpl implements IndustryService {

	private static final String INVALID_SLUG_MESSAGE = "Enter a valid URL slug, e.g. manufacturing";
	private static final String DUPLICATE_SLUG_MESSAGE = "This URL slug is already used by another industry";

	private IndustryRepository industryRepository;

	// ========================= List =========================

	@Override
	@Transactional(readOnly = true)
	public List<IndustryResponse> getAllIndustries() {
		List<IndustryEntity> entities = industryRepository.findAllByOrderBySortOrderAsc();
		return entities.isEmpty() ? null : entities.stream().map(this::mapEntityToResponse).toList();
	}

	// ========================= Save =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveIndustry(IndustryRequest request) throws CAException {

		boolean isNew = request.getIndustryId() == null;
		IndustryEntity entity;
		if (isNew) {
			entity = new IndustryEntity();
		} else {
			entity = industryRepository.findById(request.getIndustryId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		String slug = CommonUtil.resolveSlug(request.getSlug(), request.getName());
		if (slug == null) {
			throw CAException.badRequest(INVALID_SLUG_MESSAGE);
		}
		boolean slugTaken = isNew ? industryRepository.existsBySlug(slug)
				: industryRepository.existsBySlugAndIdNot(slug, entity.getId());
		if (slugTaken) {
			throw CAException.conflict(DUPLICATE_SLUG_MESSAGE);
		}

		entity.setName(request.getName().trim());
		entity.setSlug(slug);
		entity.setSummary(CommonUtil.trimToNull(request.getSummary()));
		entity.setIcon(CommonUtil.trimToNull(request.getIcon()));
		if (request.getSortOrder() != null || isNew) {
			entity.setSortOrder(CommonUtil.orZero(request.getSortOrder()));
		}
		industryRepository.save(entity);
		return true;
	}

	// ========================= Delete (hard) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteIndustry(Long industryId) {
		if (!industryRepository.existsById(industryId)) {
			return false;
		}
		industryRepository.deleteById(industryId);
		log.info("Industry {} deleted", industryId);
		return true;
	}

	// ========================= Mapping =========================

	private IndustryResponse mapEntityToResponse(IndustryEntity e) {
		IndustryResponse r = new IndustryResponse();
		r.setIndustryId(e.getId());
		r.setName(e.getName());
		r.setSlug(e.getSlug());
		r.setSummary(e.getSummary());
		r.setIcon(e.getIcon());
		r.setSortOrder(e.getSortOrder());
		return r;
	}

}

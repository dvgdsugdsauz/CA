package com.ca.charteredAccountant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.FaqEntity;
import com.ca.charteredAccountant.dao.model.FirmServiceEntity;
import com.ca.charteredAccountant.dao.model.OfficeLocationEntity;
import com.ca.charteredAccountant.exception.CAException;
import com.ca.charteredAccountant.repository.FaqRepository;
import com.ca.charteredAccountant.repository.FirmServiceRepository;
import com.ca.charteredAccountant.repository.OfficeLocationRepository;
import com.ca.charteredAccountant.request.FaqRequest;
import com.ca.charteredAccountant.response.FaqResponse;
import com.ca.charteredAccountant.service.FaqService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FaqServiceImpl implements FaqService {

	private static final String INVALID_CITY_MESSAGE = "Select a valid city";
	private static final String INVALID_SERVICE_MESSAGE = "Select a valid service";

	private FaqRepository faqRepository;
	private OfficeLocationRepository officeLocationRepository;
	private FirmServiceRepository firmServiceRepository;

	// ========================= Public (website) =========================

	@Override
	@Transactional(readOnly = true)
	public List<FaqResponse> getActiveFaqs(String locationSlug, String serviceSlug) {
		String service = CommonUtil.trimToNull(serviceSlug);
		List<FaqEntity> faqs = service != null
				? faqRepository.findActiveByServiceSlug(service)
				: faqRepository.findActiveGeneral(CommonUtil.trimToNull(locationSlug));
		return faqs.isEmpty() ? null : faqs.stream().map(this::mapEntityToResponse).toList();
	}

	// ========================= List (back office) =========================

	@Override
	@Transactional(readOnly = true)
	public List<FaqResponse> getAllFaqs() {
		List<FaqEntity> faqs = faqRepository.findAllByOrderBySortOrderAscIdAsc();
		return faqs.isEmpty() ? null : faqs.stream().map(this::mapEntityToResponse).toList();
	}

	// ========================= Save (create / update) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveFaq(FaqRequest request) throws CAException {

		FaqEntity entity;
		if (request.getFaqId() == null) {
			entity = new FaqEntity();
		} else {
			entity = faqRepository.findById(request.getFaqId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		OfficeLocationEntity location = null;
		if (request.getLocationId() != null) {
			location = officeLocationRepository.findById(request.getLocationId())
					.orElseThrow(() -> CAException.badRequest(INVALID_CITY_MESSAGE));
		}
		FirmServiceEntity service = null;
		if (request.getServiceId() != null) {
			service = firmServiceRepository.findById(request.getServiceId())
					.orElseThrow(() -> CAException.badRequest(INVALID_SERVICE_MESSAGE));
		}

		entity.setQuestion(request.getQuestion().trim());
		entity.setAnswer(request.getAnswer().trim());
		entity.setLocation(location);
		entity.setService(service);
		entity.setActive(request.getActive() != null ? request.getActive()
				: (entity.getActive() != null ? entity.getActive() : Constants.ACTIVE));
		entity.setSortOrder(request.getSortOrder() != null ? request.getSortOrder()
				: CommonUtil.orZero(entity.getSortOrder()));

		faqRepository.save(entity);
		return true;
	}

	// ========================= Delete (soft) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteFaq(Long faqId) {
		FaqEntity entity = faqRepository.findById(faqId).orElse(null);
		if (entity == null) {
			return false;
		}
		entity.setActive(Constants.INACTIVE);
		faqRepository.save(entity);
		return true;
	}

	// ========================= Mapping =========================

	private FaqResponse mapEntityToResponse(FaqEntity e) {
		FaqResponse r = new FaqResponse();
		r.setFaqId(e.getId());
		r.setQuestion(e.getQuestion());
		r.setAnswer(e.getAnswer());
		r.setLocationId(e.getLocation() != null ? e.getLocation().getId() : null);
		r.setCity(e.getLocation() != null ? e.getLocation().getCity() : null);
		r.setServiceId(e.getService() != null ? e.getService().getId() : null);
		r.setServiceTitle(e.getService() != null ? e.getService().getTitle() : null);
		r.setActive(e.getActive());
		r.setSortOrder(e.getSortOrder());
		return r;
	}

}

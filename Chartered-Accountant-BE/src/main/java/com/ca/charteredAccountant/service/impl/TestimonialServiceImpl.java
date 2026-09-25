package com.ca.charteredAccountant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.TestimonialEntity;
import com.ca.charteredAccountant.repository.TestimonialRepository;
import com.ca.charteredAccountant.request.TestimonialRequest;
import com.ca.charteredAccountant.response.TestimonialResponse;
import com.ca.charteredAccountant.service.TestimonialService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

	private TestimonialRepository testimonialRepository;

	// ========================= Public (website) =========================

	@Override
	@Transactional(readOnly = true)
	public List<TestimonialResponse> getActiveTestimonials() {
		List<TestimonialEntity> testimonials = testimonialRepository
				.findAllByActiveOrderBySortOrderAscIdAsc(Constants.ACTIVE);
		return testimonials.isEmpty() ? null : testimonials.stream().map(this::mapEntityToResponse).toList();
	}

	// ========================= List (back office) =========================

	@Override
	@Transactional(readOnly = true)
	public List<TestimonialResponse> getAllTestimonials() {
		List<TestimonialEntity> testimonials = testimonialRepository.findAllByOrderBySortOrderAscIdAsc();
		return testimonials.isEmpty() ? null : testimonials.stream().map(this::mapEntityToResponse).toList();
	}

	// ========================= Save (create / update) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveTestimonial(TestimonialRequest request) {

		TestimonialEntity entity;
		if (request.getTestimonialId() == null) {
			entity = new TestimonialEntity();
		} else {
			entity = testimonialRepository.findById(request.getTestimonialId()).orElse(null);
			if (entity == null) {
				return null;
			}
		}

		entity.setAuthorName(request.getAuthorName().trim());
		entity.setAuthorTitle(CommonUtil.trimToNull(request.getAuthorTitle()));
		entity.setQuote(request.getQuote().trim());
		entity.setRating(request.getRating());
		entity.setActive(request.getActive() != null ? request.getActive()
				: (entity.getActive() != null ? entity.getActive() : Constants.ACTIVE));
		entity.setSortOrder(request.getSortOrder() != null ? request.getSortOrder()
				: CommonUtil.orZero(entity.getSortOrder()));

		testimonialRepository.save(entity);
		return true;
	}

	// ========================= Delete (soft) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteTestimonial(Long testimonialId) {
		TestimonialEntity entity = testimonialRepository.findById(testimonialId).orElse(null);
		if (entity == null) {
			return false;
		}
		entity.setActive(Constants.INACTIVE);
		testimonialRepository.save(entity);
		return true;
	}

	// ========================= Mapping =========================

	private TestimonialResponse mapEntityToResponse(TestimonialEntity e) {
		TestimonialResponse r = new TestimonialResponse();
		r.setTestimonialId(e.getId());
		r.setAuthorName(e.getAuthorName());
		r.setAuthorTitle(e.getAuthorTitle());
		r.setQuote(e.getQuote());
		r.setRating(e.getRating());
		r.setActive(e.getActive());
		r.setSortOrder(e.getSortOrder());
		return r;
	}

}

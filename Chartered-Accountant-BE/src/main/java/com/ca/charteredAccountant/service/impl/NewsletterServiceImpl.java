package com.ca.charteredAccountant.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ca.charteredAccountant.common.Constants;
import com.ca.charteredAccountant.dao.model.NewsletterSubscriberEntity;
import com.ca.charteredAccountant.repository.NewsletterSubscriberRepository;
import com.ca.charteredAccountant.request.NewsletterRequest;
import com.ca.charteredAccountant.response.NewsletterSubscriberResponse;
import com.ca.charteredAccountant.response.PageResponse;
import com.ca.charteredAccountant.service.NewsletterService;
import com.ca.charteredAccountant.util.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {

	private NewsletterSubscriberRepository newsletterSubscriberRepository;

	// ========================= Subscribe (public) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String subscribe(NewsletterRequest request) {

		String email = CommonUtil.normalizeEmail(request.getEmail());
		NewsletterSubscriberEntity entity = newsletterSubscriberRepository.findByEmail(email).orElse(null);

		if (entity != null && Boolean.TRUE.equals(entity.getActive())) {
			return Constants.ResponseMessages.ALREADY_SUBSCRIBED_MESSAGE;
		}

		if (entity == null) {
			entity = NewsletterSubscriberEntity.builder().email(email).build();
		}
		entity.setActive(true);
		entity.setSubscribedAt(LocalDateTime.now());
		entity.setUnsubscribedAt(null);
		newsletterSubscriberRepository.save(entity);
		return Constants.ResponseMessages.SUBSCRIBED_MESSAGE;
	}

	// ========================= Unsubscribe (public) =========================

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void unsubscribe(NewsletterRequest request) {
		newsletterSubscriberRepository.findByEmail(CommonUtil.normalizeEmail(request.getEmail()))
				.filter(s -> Boolean.TRUE.equals(s.getActive()))
				.ifPresent(s -> {
					s.setActive(false);
					s.setUnsubscribedAt(LocalDateTime.now());
					newsletterSubscriberRepository.save(s);
				});
	}

	// ========================= List (back office) =========================

	@Override
	@Transactional(readOnly = true)
	public PageResponse<NewsletterSubscriberResponse> getAllSubscribers(Boolean active, Pageable pageable) {
		return PageResponse.of(newsletterSubscriberRepository.searchSubscribers(active, pageable),
				this::mapEntityToResponse);
	}

	// ========================= Mapping =========================

	private NewsletterSubscriberResponse mapEntityToResponse(NewsletterSubscriberEntity e) {
		NewsletterSubscriberResponse r = new NewsletterSubscriberResponse();
		r.setSubscriberId(e.getId());
		r.setEmail(e.getEmail());
		r.setActive(e.getActive());
		r.setSubscribedAt(e.getSubscribedAt());
		r.setUnsubscribedAt(e.getUnsubscribedAt());
		return r;
	}

}

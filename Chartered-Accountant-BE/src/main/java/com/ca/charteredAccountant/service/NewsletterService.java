package com.ca.charteredAccountant.service;

import org.springframework.data.domain.Pageable;

import com.ca.charteredAccountant.request.NewsletterRequest;
import com.ca.charteredAccountant.response.NewsletterSubscriberResponse;
import com.ca.charteredAccountant.response.PageResponse;

public interface NewsletterService {

	/** Returns the message to show: subscribed, or already subscribed. */
	String subscribe(NewsletterRequest request);

	void unsubscribe(NewsletterRequest request);

	PageResponse<NewsletterSubscriberResponse> getAllSubscribers(Boolean active, Pageable pageable);

}

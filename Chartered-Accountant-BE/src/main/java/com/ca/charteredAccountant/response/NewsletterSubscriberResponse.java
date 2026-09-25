package com.ca.charteredAccountant.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsletterSubscriberResponse {

	private Long subscriberId;
	private String email;
	private Boolean active;
	private LocalDateTime subscribedAt;
	private LocalDateTime unsubscribedAt;

}

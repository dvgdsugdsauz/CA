package com.ca.charteredAccountant.dao.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "newsletter_subscriber")
public class NewsletterSubscriberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "email", nullable = false, unique = true, length = 120)
	private String email;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@Column(name = "subscribed_at", nullable = false)
	private LocalDateTime subscribedAt;

	@Column(name = "unsubscribed_at")
	private LocalDateTime unsubscribedAt;

}

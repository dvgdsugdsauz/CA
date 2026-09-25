package com.ca.charteredAccountant.dao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "faq")
public class FaqEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "question", nullable = false, length = 300)
	private String question;

	@Column(name = "answer", nullable = false, columnDefinition = "TEXT")
	private String answer;

	/** Null = shown on every city page. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private OfficeLocationEntity location;

	/** Null = general FAQ, not tied to a service page. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id")
	private FirmServiceEntity service;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@Column(name = "sort_order", nullable = false)
	private Integer sortOrder;

}

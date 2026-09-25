package com.ca.charteredAccountant.dao.model;

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
@Table(name = "testimonial")
public class TestimonialEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "author_name", nullable = false, length = 120)
	private String authorName;

	@Column(name = "author_title", length = 160)
	private String authorTitle;

	@Column(name = "quote", nullable = false, columnDefinition = "TEXT")
	private String quote;

	@Column(name = "rating")
	private Integer rating;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@Column(name = "sort_order", nullable = false)
	private Integer sortOrder;

}

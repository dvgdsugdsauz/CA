package com.ca.charteredAccountant.dao.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ca.charteredAccountant.common.enums.EmploymentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "job_opening")
public class JobOpeningEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title", nullable = false, length = 160)
	private String title;

	@Column(name = "slug", nullable = false, unique = true, length = 160)
	private String slug;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private OfficeLocationEntity location;

	@Column(name = "department", length = 80)
	private String department;

	@Column(name = "experience_range", length = 60)
	private String experienceRange;

	@Enumerated(EnumType.STRING)
	@Column(name = "employment_type", nullable = false, length = 20)
	private EmploymentType employmentType;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@Column(name = "posted_at", nullable = false)
	private LocalDateTime postedAt;

	@Column(name = "closes_on")
	private LocalDate closesOn;

}

package com.ca.charteredAccountant.dao.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.ca.charteredAccountant.common.enums.ApplicationStatus;

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
@Table(name = "job_application")
public class JobApplicationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "job_id", nullable = false)
	private JobOpeningEntity job;

	@Column(name = "full_name", nullable = false, length = 120)
	private String fullName;

	@Column(name = "email", nullable = false, length = 120)
	private String email;

	@Column(name = "phone", nullable = false, length = 20)
	private String phone;

	@Column(name = "qualification", length = 80)
	private String qualification;

	@Column(name = "resume_path", length = 500)
	private String resumePath;

	@Column(name = "cover_note", columnDefinition = "TEXT")
	private String coverNote;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private ApplicationStatus status;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

}

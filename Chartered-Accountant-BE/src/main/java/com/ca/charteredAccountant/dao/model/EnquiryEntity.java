package com.ca.charteredAccountant.dao.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ca.charteredAccountant.common.enums.EnquiryStatus;

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
@Table(name = "enquiry")
public class EnquiryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "reference_no", unique = true, length = 20)
	private String referenceNo;

	@Column(name = "full_name", nullable = false, length = 120)
	private String fullName;

	@Column(name = "email", nullable = false, length = 120)
	private String email;

	@Column(name = "phone", nullable = false, length = 20)
	private String phone;

	@Column(name = "company_name", length = 160)
	private String companyName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id")
	private FirmServiceEntity service;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private OfficeLocationEntity location;

	@Column(name = "message", columnDefinition = "TEXT")
	private String message;

	@Column(name = "source_page", length = 255)
	private String sourcePage;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private EnquiryStatus status;

	@Column(name = "internal_notes", columnDefinition = "TEXT")
	private String internalNotes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assigned_to_id")
	private AdminUserEntity assignedTo;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

}

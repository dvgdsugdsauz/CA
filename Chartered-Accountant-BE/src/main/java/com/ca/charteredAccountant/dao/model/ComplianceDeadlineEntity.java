package com.ca.charteredAccountant.dao.model;

import java.time.LocalDate;

import com.ca.charteredAccountant.common.enums.ComplianceLaw;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "compliance_deadline")
public class ComplianceDeadlineEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@Column(name = "title", nullable = false, length = 200)
	private String title;

	@Enumerated(EnumType.STRING)
	@Column(name = "law", nullable = false, length = 20)
	private ComplianceLaw law;

	@Column(name = "applies_to", length = 200)
	private String appliesTo;

	@Column(name = "active", nullable = false)
	private Boolean active;

}

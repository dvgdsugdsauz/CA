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
@Table(name = "office_location")
public class OfficeLocationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "city", nullable = false, length = 80)
	private String city;

	@Column(name = "slug", nullable = false, unique = true, length = 80)
	private String slug;

	@Column(name = "hero_heading", length = 160)
	private String heroHeading;

	@Column(name = "intro", columnDefinition = "TEXT")
	private String intro;

	@Column(name = "areas_served", length = 300)
	private String areasServed;

	@Column(name = "address_line", nullable = false, length = 300)
	private String addressLine;

	@Column(name = "phone", length = 20)
	private String phone;

	@Column(name = "email", length = 120)
	private String email;

	@Column(name = "office_hours", length = 120)
	private String officeHours;

	@Column(name = "map_url", length = 500)
	private String mapUrl;

	@Column(name = "meta_title", length = 160)
	private String metaTitle;

	@Column(name = "meta_description", length = 300)
	private String metaDescription;

	@Column(name = "head_office", nullable = false)
	private Boolean headOffice;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@Column(name = "sort_order", nullable = false)
	private Integer sortOrder;

}

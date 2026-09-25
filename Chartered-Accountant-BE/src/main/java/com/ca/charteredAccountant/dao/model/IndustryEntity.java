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
@Table(name = "industry")
public class IndustryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false, length = 80)
	private String name;

	@Column(name = "slug", nullable = false, unique = true, length = 80)
	private String slug;

	@Column(name = "summary", length = 300)
	private String summary;

	@Column(name = "icon", length = 40)
	private String icon;

	@Column(name = "sort_order", nullable = false)
	private Integer sortOrder;

}

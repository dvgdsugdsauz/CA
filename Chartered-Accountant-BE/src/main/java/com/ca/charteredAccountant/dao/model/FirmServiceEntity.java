package com.ca.charteredAccountant.dao.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
@Table(name = "firm_service")
public class FirmServiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private ServiceCategoryEntity category;

	@Column(name = "title", nullable = false, length = 120)
	private String title;

	@Column(name = "slug", nullable = false, unique = true, length = 120)
	private String slug;

	@Column(name = "summary", nullable = false, length = 400)
	private String summary;

	@Column(name = "body", columnDefinition = "TEXT")
	private String body;

	@Column(name = "icon", length = 40)
	private String icon;

	@Column(name = "meta_title", length = 160)
	private String metaTitle;

	@Column(name = "meta_description", length = 300)
	private String metaDescription;

	@Column(name = "featured", nullable = false)
	private Boolean featured;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@Column(name = "sort_order", nullable = false)
	private Integer sortOrder;

	/** The "what is included" bullets. Replaced as a whole on save. */
	@Builder.Default
	@OrderBy("sortOrder ASC")
	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ServiceHighlightEntity> highlights = new ArrayList<>();

}

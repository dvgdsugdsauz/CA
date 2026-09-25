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
@Table(name = "team_member")
public class TeamMemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "full_name", nullable = false, length = 120)
	private String fullName;

	@Column(name = "designation", nullable = false, length = 120)
	private String designation;

	@Column(name = "qualifications", length = 160)
	private String qualifications;

	@Column(name = "bio", columnDefinition = "TEXT")
	private String bio;

	@Column(name = "photo_url", length = 500)
	private String photoUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private OfficeLocationEntity location;

	@Column(name = "active", nullable = false)
	private Boolean active;

	@Column(name = "sort_order", nullable = false)
	private Integer sortOrder;

}

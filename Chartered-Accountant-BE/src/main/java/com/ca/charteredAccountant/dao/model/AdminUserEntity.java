package com.ca.charteredAccountant.dao.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.ca.charteredAccountant.common.enums.AdminRole;

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
@Table(name = "admin_user")
public class AdminUserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username", nullable = false, unique = true, length = 60)
	private String username;

	@Column(name = "password_hash", nullable = false, length = 100)
	private String passwordHash;

	@Column(name = "full_name", nullable = false, length = 120)
	private String fullName;

	@Column(name = "email", nullable = false, length = 120)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20)
	private AdminRole role;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;

}

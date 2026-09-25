package com.ca.charteredAccountant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ca.charteredAccountant.common.enums.AdminRole;
import com.ca.charteredAccountant.dao.model.AdminUserEntity;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Long> {

	Optional<AdminUserEntity> findByUsername(String username);

	boolean existsByUsernameAndIdNot(String username, Long id);

	boolean existsByUsername(String username);

	List<AdminUserEntity> findAllByOrderByFullNameAsc();

	List<AdminUserEntity> findAllByEnabledOrderByFullNameAsc(Boolean enabled);

	long countByRoleAndEnabled(AdminRole role, Boolean enabled);

}

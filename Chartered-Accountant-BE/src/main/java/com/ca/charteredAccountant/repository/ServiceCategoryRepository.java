package com.ca.charteredAccountant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ca.charteredAccountant.dao.model.ServiceCategoryEntity;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategoryEntity, Long> {

	List<ServiceCategoryEntity> findAllByOrderBySortOrderAsc();

	boolean existsBySlug(String slug);

	boolean existsBySlugAndIdNot(String slug, Long id);

}

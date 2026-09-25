package com.ca.charteredAccountant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ca.charteredAccountant.dao.model.IndustryEntity;

public interface IndustryRepository extends JpaRepository<IndustryEntity, Long> {

	List<IndustryEntity> findAllByOrderBySortOrderAsc();

	boolean existsBySlug(String slug);

	boolean existsBySlugAndIdNot(String slug, Long id);

}

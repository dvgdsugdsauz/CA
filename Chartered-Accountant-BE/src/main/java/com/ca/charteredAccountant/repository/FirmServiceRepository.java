package com.ca.charteredAccountant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ca.charteredAccountant.dao.model.FirmServiceEntity;

public interface FirmServiceRepository extends JpaRepository<FirmServiceEntity, Long> {

	@EntityGraph(attributePaths = { "category" })
	List<FirmServiceEntity> findAllByActiveOrderBySortOrderAsc(Boolean active);

	@EntityGraph(attributePaths = { "category" })
	List<FirmServiceEntity> findAllByOrderBySortOrderAsc();

	@EntityGraph(attributePaths = { "category", "highlights" })
	Optional<FirmServiceEntity> findBySlugAndActive(String slug, Boolean active);

	@EntityGraph(attributePaths = { "category", "highlights" })
	Optional<FirmServiceEntity> findWithDetailsById(Long id);

	boolean existsBySlug(String slug);

	boolean existsBySlugAndIdNot(String slug, Long id);

	boolean existsByCategoryId(Long categoryId);

}

package com.ca.charteredAccountant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ca.charteredAccountant.dao.model.FaqEntity;

public interface FaqRepository extends JpaRepository<FaqEntity, Long> {

//	=========================== Public (website) ========================

	@EntityGraph(attributePaths = { "location", "service" })
	@Query("""
			SELECT f FROM FaqEntity f
			JOIN f.service s
			WHERE f.active = true
			  AND s.slug = :serviceSlug
			ORDER BY f.sortOrder ASC, f.id ASC
			""")
	List<FaqEntity> findActiveByServiceSlug(@Param("serviceSlug") String serviceSlug);

	@EntityGraph(attributePaths = { "location", "service" })
	@Query("""
			SELECT f FROM FaqEntity f
			LEFT JOIN f.location l
			WHERE f.active = true
			  AND f.service IS NULL
			  AND (l.id IS NULL OR (:locationSlug IS NOT NULL AND l.slug = :locationSlug))
			ORDER BY f.sortOrder ASC, f.id ASC
			""")
	List<FaqEntity> findActiveGeneral(@Param("locationSlug") String locationSlug);

//	=========================== Back office ========================

	@EntityGraph(attributePaths = { "location", "service" })
	List<FaqEntity> findAllByOrderBySortOrderAscIdAsc();

}

package com.ca.charteredAccountant.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ca.charteredAccountant.dao.model.JobOpeningEntity;

public interface JobOpeningRepository extends JpaRepository<JobOpeningEntity, Long> {

//	=========================== Public (careers page) ========================

	@EntityGraph(attributePaths = { "location" })
	@Query("""
			SELECT j FROM JobOpeningEntity j
			WHERE j.active = true
			  AND (j.closesOn IS NULL OR j.closesOn >= :today)
			ORDER BY j.postedAt DESC
			""")
	List<JobOpeningEntity> findOpenJobs(@Param("today") LocalDate today);

	@EntityGraph(attributePaths = { "location" })
	@Query("""
			SELECT j FROM JobOpeningEntity j
			WHERE j.slug = :slug
			  AND j.active = true
			  AND (j.closesOn IS NULL OR j.closesOn >= :today)
			""")
	Optional<JobOpeningEntity> findOpenBySlug(@Param("slug") String slug, @Param("today") LocalDate today);

//	=========================== Back office ========================

	@EntityGraph(attributePaths = { "location" })
	List<JobOpeningEntity> findAllByOrderByPostedAtDesc();

	@EntityGraph(attributePaths = { "location" })
	Optional<JobOpeningEntity> findWithLocationById(Long id);

	boolean existsBySlug(String slug);

	boolean existsBySlugAndIdNot(String slug, Long id);

}

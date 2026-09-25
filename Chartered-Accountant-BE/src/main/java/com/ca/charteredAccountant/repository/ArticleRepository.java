package com.ca.charteredAccountant.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ca.charteredAccountant.common.enums.ArticleStatus;
import com.ca.charteredAccountant.dao.model.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

//	=========================== Public ========================

	@Query("""
			SELECT a FROM ArticleEntity a
			WHERE a.status = :status
			  AND a.publishedAt IS NOT NULL
			  AND a.publishedAt <= :now
			  AND (:category IS NULL OR a.category = :category)
			""")
	Page<ArticleEntity> findPublished(
			@Param("status") ArticleStatus status,
			@Param("now") LocalDateTime now,
			@Param("category") String category,
			Pageable pageable);

	Optional<ArticleEntity> findBySlugAndStatusAndPublishedAtLessThanEqual(String slug, ArticleStatus status,
			LocalDateTime now);

//	=========================== Back office ========================

	@Query("""
			SELECT a FROM ArticleEntity a
			WHERE (:status IS NULL OR a.status = :status)
			  AND (:search IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :search, '%')))
			""")
	Page<ArticleEntity> searchArticles(
			@Param("status") ArticleStatus status,
			@Param("search") String search,
			Pageable pageable);

//	=========================== Slug checks ========================

	boolean existsBySlug(String slug);

	boolean existsBySlugAndIdNot(String slug, Long id);

}

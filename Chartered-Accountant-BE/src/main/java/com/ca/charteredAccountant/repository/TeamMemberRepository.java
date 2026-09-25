package com.ca.charteredAccountant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ca.charteredAccountant.dao.model.TeamMemberEntity;

public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, Long> {

//	=========================== Public (website) ========================

	@EntityGraph(attributePaths = { "location" })
	@Query("""
			SELECT t FROM TeamMemberEntity t
			LEFT JOIN t.location l
			WHERE t.active = true
			  AND (:locationSlug IS NULL OR l.slug = :locationSlug)
			ORDER BY t.sortOrder ASC, t.id ASC
			""")
	List<TeamMemberEntity> findActive(@Param("locationSlug") String locationSlug);

//	=========================== Back office ========================

	@EntityGraph(attributePaths = { "location" })
	List<TeamMemberEntity> findAllByOrderBySortOrderAscIdAsc();

}

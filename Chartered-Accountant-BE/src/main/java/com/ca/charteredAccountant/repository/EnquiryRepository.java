package com.ca.charteredAccountant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ca.charteredAccountant.common.enums.EnquiryStatus;
import com.ca.charteredAccountant.dao.model.EnquiryEntity;

public interface EnquiryRepository extends JpaRepository<EnquiryEntity, Long> {

//	=========================== List (back office) ========================

	@EntityGraph(attributePaths = { "service", "location", "assignedTo" })
	@Query("""
			SELECT e FROM EnquiryEntity e
			WHERE (:status IS NULL OR e.status = :status)
			  AND (:search IS NULL
			       OR LOWER(e.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
			       OR LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%'))
			       OR e.phone LIKE CONCAT('%', :search, '%')
			       OR LOWER(e.companyName) LIKE LOWER(CONCAT('%', :search, '%'))
			       OR e.referenceNo LIKE CONCAT('%', :search, '%'))
			""")
	Page<EnquiryEntity> searchEnquiries(
			@Param("status") EnquiryStatus status,
			@Param("search") String search,
			Pageable pageable);

//	=========================== Status Counts ========================

	@Query("SELECT e.status, COUNT(e) FROM EnquiryEntity e GROUP BY e.status")
	List<Object[]> countGroupByStatus();

//	=========================== Get Single ========================

	@EntityGraph(attributePaths = { "service", "location", "assignedTo" })
	Optional<EnquiryEntity> findWithDetailsById(Long id);

}

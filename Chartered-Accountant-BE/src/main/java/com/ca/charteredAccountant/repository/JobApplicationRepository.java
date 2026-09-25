package com.ca.charteredAccountant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ca.charteredAccountant.common.enums.ApplicationStatus;
import com.ca.charteredAccountant.dao.model.JobApplicationEntity;

public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Long> {

//	=========================== List (back office) ========================

	@EntityGraph(attributePaths = { "job" })
	@Query("""
			SELECT a FROM JobApplicationEntity a
			WHERE (:jobId IS NULL OR a.job.id = :jobId)
			  AND (:status IS NULL OR a.status = :status)
			""")
	Page<JobApplicationEntity> searchApplications(
			@Param("jobId") Long jobId,
			@Param("status") ApplicationStatus status,
			Pageable pageable);

}

package com.ca.charteredAccountant.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ca.charteredAccountant.dao.model.ComplianceDeadlineEntity;

public interface ComplianceDeadlineRepository extends JpaRepository<ComplianceDeadlineEntity, Long> {

	List<ComplianceDeadlineEntity> findAllByActiveAndDueDateGreaterThanEqualOrderByDueDateAscIdAsc(
			Boolean active, LocalDate fromDate, Pageable pageable);

	List<ComplianceDeadlineEntity> findAllByOrderByDueDateDescIdDesc();

}

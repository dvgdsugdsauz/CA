package com.ca.charteredAccountant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ca.charteredAccountant.dao.model.TestimonialEntity;

public interface TestimonialRepository extends JpaRepository<TestimonialEntity, Long> {

	List<TestimonialEntity> findAllByActiveOrderBySortOrderAscIdAsc(Boolean active);

	List<TestimonialEntity> findAllByOrderBySortOrderAscIdAsc();

}

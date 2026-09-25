package com.ca.charteredAccountant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ca.charteredAccountant.dao.model.OfficeLocationEntity;

public interface OfficeLocationRepository extends JpaRepository<OfficeLocationEntity, Long> {

	List<OfficeLocationEntity> findAllByActiveOrderBySortOrderAsc(Boolean active);

	List<OfficeLocationEntity> findAllByOrderBySortOrderAsc();

	Optional<OfficeLocationEntity> findBySlugAndActive(String slug, Boolean active);

	boolean existsBySlug(String slug);

	boolean existsBySlugAndIdNot(String slug, Long id);

//	=========================== Head Office ========================

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("""
			UPDATE OfficeLocationEntity o SET o.headOffice = false
			WHERE o.headOffice = true AND o.id <> :id
			""")
	int clearHeadOfficeExcept(@Param("id") Long id);

}

package com.ca.charteredAccountant.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ca.charteredAccountant.dao.model.NewsletterSubscriberEntity;

public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriberEntity, Long> {

	Optional<NewsletterSubscriberEntity> findByEmail(String email);

	@Query("""
			SELECT s FROM NewsletterSubscriberEntity s
			WHERE (:active IS NULL OR s.active = :active)
			""")
	Page<NewsletterSubscriberEntity> searchSubscribers(@Param("active") Boolean active, Pageable pageable);

}

package org.pf.school.repository;

import java.util.Date;
import java.util.UUID;

import org.pf.school.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepo extends JpaRepository<Document, UUID>{
	
	Page<Document> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	Page<Document> findByPubEndDateAfterAndSearchStringContainingIgnoreCase(Date date, String searchString, Pageable pageable);
	
	Page<Document> findAllByPubEndDateAfter(Date date, Pageable pageable);
	
}

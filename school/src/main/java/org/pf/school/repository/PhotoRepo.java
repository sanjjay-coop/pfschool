package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo, UUID>{
	
	Page<Photo> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}


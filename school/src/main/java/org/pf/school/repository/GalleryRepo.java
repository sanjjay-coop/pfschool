package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepo extends JpaRepository<Gallery, UUID>{
	
	Page<Gallery> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}


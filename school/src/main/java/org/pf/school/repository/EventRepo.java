package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, UUID>{
	
	Page<Event> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}

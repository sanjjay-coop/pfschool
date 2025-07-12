package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contact, UUID>{
	
	Page<Contact> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}

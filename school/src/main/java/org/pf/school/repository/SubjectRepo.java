package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepo extends JpaRepository<Subject, UUID>{
	
	public Subject findByCodeIgnoreCase(String code);
	
	public Subject findByNameIgnoreCase(String name);
}

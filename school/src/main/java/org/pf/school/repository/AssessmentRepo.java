package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.AcademicSession;
import org.pf.school.model.Assessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepo extends JpaRepository<Assessment, UUID>{
	
	public List<Assessment> findAllBySession(AcademicSession session);
	
	public Assessment findBySessionAndNameIgnoreCase(AcademicSession session, String name);
	
	Page<Assessment> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
}

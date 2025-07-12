package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentResultRepo extends JpaRepository<AssessmentResult, UUID>{
	
}

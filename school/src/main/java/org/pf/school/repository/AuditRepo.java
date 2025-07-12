package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepo extends JpaRepository<Audit, UUID>{
	
}

package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepo extends JpaRepository<Gender, UUID>{
	
	Gender findByCodeIgnoreCase(String code);
	Gender findByNameIgnoreCase(String name);
}

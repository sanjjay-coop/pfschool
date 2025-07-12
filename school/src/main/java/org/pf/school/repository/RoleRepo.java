package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, UUID>{
	
	Role findByCodeIgnoreCase(String code);
	Role findByDescriptionIgnoreCase(String description);
}

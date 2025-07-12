package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, UUID>{
	
	Category findByCodeIgnoreCase(String code);
	Category findByNameIgnoreCase(String name);
}

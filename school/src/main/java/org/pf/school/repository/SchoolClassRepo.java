package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassRepo extends JpaRepository<SchoolClass, UUID>{
	
	SchoolClass findByCodeIgnoreCase(String code);
	SchoolClass findByNameIgnoreCase(String name);
	List<SchoolClass> findAllByOrderBySeqNumberAsc();
}

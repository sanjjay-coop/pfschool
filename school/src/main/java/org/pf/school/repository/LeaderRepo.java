package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.Leader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LeaderRepo extends JpaRepository<Leader, UUID>{
	
	Page<Leader> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	Leader findByNameIgnoreCase(String name);

	@Query("select i from Leader i order by RANDOM() LIMIT 1")
	public List<Leader> findRandomLeader();
}


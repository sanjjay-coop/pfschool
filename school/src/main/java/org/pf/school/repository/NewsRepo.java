package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsRepo extends JpaRepository<News, UUID>{

	Page<News> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	@Query("select i from News i order by recordAddDate DESC LIMIT 5")
	public List<News> findLatestNews();
}
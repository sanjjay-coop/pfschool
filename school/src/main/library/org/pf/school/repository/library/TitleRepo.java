package org.pf.school.repository.library;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.library.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TitleRepo extends JpaRepository<Title, UUID>{

	public Title findByAccessionNumber(String accessionNumber);

	@Query("select t from Title t "
			+ "where lower(t.accessionNumber) like %:str% or "
			+ "lower(t.uniformTitle) like %:str% or "
			+ "lower(t.authors) like %:str% order by t.uniformTitle asc")
	public List<Title> listTitle(String str);
	
	@Query("select t from Title t order by t.id desc limit 5")
	public List<Title> listTitleRecent();
	
	Page<Title> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}
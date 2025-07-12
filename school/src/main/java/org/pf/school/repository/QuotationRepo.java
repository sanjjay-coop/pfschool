package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuotationRepo extends JpaRepository<Quotation, UUID>{
	
	@Query("select i from Quotation i order by RANDOM() LIMIT 1")
	public List<Quotation> findRandomQuotation();
	
	Page<Quotation> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	public Quotation findByQuoteIgnoreCase(String quote);
	
	List<Quotation> findBySearchString(String searchString);
}

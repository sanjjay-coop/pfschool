package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.SiteLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SiteLogoRepo extends JpaRepository<SiteLogo, UUID>{
	
	@Query("select sl from SiteLogo sl order by sl.recordAddDate desc limit 1")
	public SiteLogo getSiteLogo();
}

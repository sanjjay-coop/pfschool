package org.pf.school.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.pf.school.model.AcademicSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AcademicSessionRepo extends JpaRepository<AcademicSession, UUID>{
	
	public AcademicSession findByTitleIgnoreCase(String title);
	
	@Query("select o "
			+ "from AcademicSession o "
			+ "where o.startDate <=:date "
			+ "and "
			+ "o.endDate >=:date "
			+ "order by o.startDate asc ")
	public List<AcademicSession> listAcademicSession(Date date);
	
}

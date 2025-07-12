package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.AcademicCalendar;
import org.pf.school.model.AcademicSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicCalendarRepo extends JpaRepository<AcademicCalendar, UUID>{
	
	List<AcademicCalendar> findAllBySessionOrderByEventDateAsc(AcademicSession session);
}

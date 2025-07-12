package org.pf.school.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.pf.school.model.AcademicSession;
import org.pf.school.model.SchoolClass;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessionDetailRepo extends JpaRepository<SessionDetail, UUID>{
	
	public SessionDetail findBySessionAndSchoolClass(AcademicSession as, SchoolClass sc);
	
	@Query("select o "
			+ "from SessionDetail o "
			+ "where o.session.startDate <=:date "
			+ "and "
			+ "o.session.endDate >=:date "
			+ "and o.classTeacher =:staff "
			+ "order by o.session.startDate asc ")
	public List<SessionDetail> listSessionDetail(Date date, Staff staff);
	
	public SessionDetail findByIdAndClassTeacher(UUID id, Staff classTeacher);
	
}

package org.pf.school.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.pf.school.model.SessionDetail;
import org.pf.school.model.SessionDetailSubjectTeacher;
import org.pf.school.model.Staff;
import org.pf.school.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessionDetailSubjectTeacherRepo extends JpaRepository<SessionDetailSubjectTeacher, UUID>{
	
	SessionDetailSubjectTeacher findBySessionDetailAndSubject(SessionDetail sessionDetail, Subject subject);
	
	SessionDetailSubjectTeacher findBySessionDetailAndSubjectAndTeacher(SessionDetail sessionDetail, Subject subject, Staff teacher);
		
	List<SessionDetailSubjectTeacher> findBySessionDetailOrderBySubject_nameAsc(SessionDetail sessionDetail);
	
	SessionDetailSubjectTeacher findByIdAndTeacher(UUID id, Staff teacher);
	
	@Query("select o "
			+ "from SessionDetailSubjectTeacher o "
			+ "where o.sessionDetail.session.startDate <=:date "
			+ "and "
			+ "o.sessionDetail.session.endDate >=:date "
			+ "and o.teacher =:staff "
			+ "order by o.sessionDetail.session.startDate asc ")
	public List<SessionDetailSubjectTeacher> listSessionDetailSubjectTeacher(Date date, Staff staff);
	
}

package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.SessionDetail;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionDetailStudentRepo extends JpaRepository<SessionDetailStudent, UUID>{
	
	List<SessionDetailStudent> findByStudentOrderBySessionDetail_session_startDateDesc(Student student);
	
	SessionDetailStudent findBySessionDetailAndStudent(SessionDetail sessionDetail, Student student);
	
	List<SessionDetailStudent> findBySessionDetailOrderByStudent_nameAsc(SessionDetail sessionDetail);
}

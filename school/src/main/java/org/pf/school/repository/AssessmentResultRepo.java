package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.Assessment;
import org.pf.school.model.AssessmentResult;
import org.pf.school.model.Student;
import org.pf.school.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentResultRepo extends JpaRepository<AssessmentResult, UUID>{
	
	AssessmentResult findByAssessmentAndStudentAndSubject(Assessment assessment, Student student, Subject subject);

	List<AssessmentResult> findByStudent(Student student);
	
}

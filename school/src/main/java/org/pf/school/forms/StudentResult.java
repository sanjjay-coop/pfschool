package org.pf.school.forms;

import org.pf.school.model.AcademicSession;
import org.pf.school.model.Assessment;
import org.pf.school.model.Student;
import org.pf.school.model.Subject;

public class StudentResult {

	private AcademicSession session;
	
	private Assessment assessment;
	
	private Student student;
	
	private Subject subject;
	
	private Integer marksObtained;
	
	private Integer maxMarks;

	public AcademicSession getSession() {
		return session;
	}

	public void setSession(AcademicSession session) {
		this.session = session;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Integer getMarksObtained() {
		return marksObtained;
	}

	public void setMarksObtained(Integer marksObtained) {
		this.marksObtained = marksObtained;
	}

	public Integer getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(Integer maxMarks) {
		this.maxMarks = maxMarks;
	}

}

package org.pf.school.forms;

import org.pf.school.model.AcademicSession;
import org.pf.school.model.Assessment;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.Student;
import org.pf.school.model.Subject;

public class AssessmentForm {

	private AcademicSession session;
	
	private SessionDetail sessionDetail;
	
	private Assessment assessment;
	
	private Student student;

	private Subject subject;
	
	private Integer maxMarks;
	
	private Integer marksObtained;

	public AcademicSession getSession() {
		return session;
	}

	public void setSession(AcademicSession session) {
		this.session = session;
	}

	public SessionDetail getSessionDetail() {
		return sessionDetail;
	}

	public void setSessionDetail(SessionDetail sessionDetail) {
		this.sessionDetail = sessionDetail;
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

	public Integer getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(Integer maxMarks) {
		this.maxMarks = maxMarks;
	}

	public Integer getMarksObtained() {
		return marksObtained;
	}

	public void setMarksObtained(Integer marksObtained) {
		this.marksObtained = marksObtained;
	}
}

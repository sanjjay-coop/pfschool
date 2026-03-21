package org.pf.school.forms;

import java.util.List;

import org.pf.school.model.Subject;

public class SubjectAssessmentMarks {

	private Subject subject;
	
	private List<AssessmentMarks> listAssessmentMarks;

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<AssessmentMarks> getListAssessmentMarks() {
		return listAssessmentMarks;
	}

	public void setListAssessmentMarks(List<AssessmentMarks> listAssessmentMarks) {
		this.listAssessmentMarks = listAssessmentMarks;
	}
	
}

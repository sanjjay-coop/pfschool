package org.pf.school.forms;

import java.util.List;

import org.pf.school.model.AcademicSession;
import org.pf.school.model.SessionDetail;

public class SessionSubjectAssessmentMarks {

	private AcademicSession session;
	
	private SessionDetail sessionDetail;
	
	private List<SubjectAssessmentMarks> listSubjectAssessmentMarks;

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

	public List<SubjectAssessmentMarks> getListSubjectAssessmentMarks() {
		return listSubjectAssessmentMarks;
	}

	public void setListSubjectAssessmentMarks(List<SubjectAssessmentMarks> listSubjectAssessmentMarks) {
		this.listSubjectAssessmentMarks = listSubjectAssessmentMarks;
	}
}

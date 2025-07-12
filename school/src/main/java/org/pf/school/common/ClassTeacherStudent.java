package org.pf.school.common;

import java.util.List;

import org.pf.school.model.AcademicSession;
import org.pf.school.model.SchoolClass;
import org.pf.school.model.Student;

public class ClassTeacherStudent implements Comparable<ClassTeacherStudent> {

	private AcademicSession session;
	
	private SchoolClass schoolClass;
	
	private List<Student> listStudent;

	public AcademicSession getSession() {
		return session;
	}

	public void setSession(AcademicSession session) {
		this.session = session;
	}

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}

	public List<Student> getListStudent() {
		return listStudent;
	}

	public void setListStudent(List<Student> listStudent) {
		this.listStudent = listStudent;
	}

	@Override
	public int compareTo(ClassTeacherStudent o) {
		if (this.session.getStartDate().compareTo(o.getSession().getStartDate())==0) {
			
			return this.schoolClass.getSeqNumber().compareTo(o.getSchoolClass().getSeqNumber());
			
		} else {
			return this.session.getStartDate().compareTo(o.getSession().getStartDate());
		}
	}
}

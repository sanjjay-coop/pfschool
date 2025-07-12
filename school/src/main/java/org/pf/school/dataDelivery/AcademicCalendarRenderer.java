package org.pf.school.dataDelivery;

import java.util.ArrayList;
import java.util.List;

import org.pf.school.model.AcademicCalendar;
import org.pf.school.model.AcademicSession;

public class AcademicCalendarRenderer implements Comparable<AcademicCalendarRenderer>{
	
	AcademicSession session;
	List<AcademicCalendar> listAcademicCalendar = new ArrayList<AcademicCalendar>();
	
	public AcademicSession getSession() {
		return session;
	}
	public void setSession(AcademicSession session) {
		this.session = session;
	}
	
	public List<AcademicCalendar> getListAcademicCalendar() {
		return listAcademicCalendar;
	}
	public void setListAcademicCalendar(List<AcademicCalendar> listAcademicCalendar) {
		this.listAcademicCalendar = listAcademicCalendar;
	}
	@Override
	public int compareTo(AcademicCalendarRenderer o) {
		// TODO Auto-generated method stub
		return (-1 * this.session.getStartDate().compareTo(o.session.getStartDate()));
	}
}

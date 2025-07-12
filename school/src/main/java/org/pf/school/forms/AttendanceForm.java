package org.pf.school.forms;

import java.util.Date;
import java.util.UUID;

public class AttendanceForm {

	private Date date;
	
	private String[][] data;
	
	private UUID sessionDetailId;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

	public UUID getSessionDetailId() {
		return sessionDetailId;
	}

	public void setSessionDetailId(UUID sessionDetailId) {
		this.sessionDetailId = sessionDetailId;
	}
	
}

package org.pf.school.forms;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

public class AttendanceForm {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	private String[][] data;
	
	private UUID sessionDetailId;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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

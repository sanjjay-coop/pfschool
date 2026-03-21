package org.pf.school.forms;

import org.pf.school.model.Assessment;

public class AssessmentMarks {

	private Assessment assessment;
	private Integer maxMarks;
	private Integer marksObtained;
	
	public Assessment getAssessment() {
		return assessment;
	}
	
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
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

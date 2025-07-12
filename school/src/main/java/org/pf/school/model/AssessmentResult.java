package org.pf.school.model;

import java.io.Serializable;
import java.util.UUID;

import org.pf.school.common.BaseObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_assessment_result",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"f_assessment", "f_student", "f_subject"})
    })
public class AssessmentResult extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 649027034843845326L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@ManyToOne()
    @JoinColumn(name = "f_assessment", nullable=false)
	private Assessment assessment;
	
	@ManyToOne()
    @JoinColumn(name = "f_student", nullable=false)
	private Student student;
	
	@ManyToOne()
    @JoinColumn(name = "f_subject", nullable=false)
	private Student subject;
	
	@Column(name="f_max_marks", nullable=false)
	private Integer maxMarks;
	
	@Column(name="f_marks_obtained", nullable=false)
	private Integer marksObtained;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Student getSubject() {
		return subject;
	}

	public void setSubject(Student subject) {
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

	@Override
	public String toString() {
		return "AssessmentResult [" + (id != null ? "id=" + id + ", " : "")
				+ (assessment != null ? "assessment=" + assessment.getId() + ", " : "")
				+ (student != null ? "student=" + student.getId() + ", " : "")
				+ (subject != null ? "subject=" + subject.getId() + ", " : "")
				+ (maxMarks != null ? "maxMarks=" + maxMarks + ", " : "")
				+ (marksObtained != null ? "marksObtained=" + marksObtained : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		if (maxMarks == null) maxMarks = Integer.valueOf(0);
		if (marksObtained == null) marksObtained = Integer.valueOf(0);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		if (maxMarks == null) maxMarks = Integer.valueOf(0);
		if (marksObtained == null) marksObtained = Integer.valueOf(0);
	}
}

package org.pf.school.model;

import java.io.Serializable;
import java.util.UUID;

import org.pf.school.common.BaseObject;

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
@Table(name="tab_session_detail_student",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"f_session_detail", "f_student"})
    })
public class SessionDetailStudent extends BaseObject implements Serializable, Comparable<SessionDetailStudent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7385536862514477527L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@ManyToOne()
    @JoinColumn(name = "f_session_detail", nullable=false)
	private SessionDetail sessionDetail;
	
	@ManyToOne()
    @JoinColumn(name = "f_student", nullable=false)
	private Student student;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public SessionDetail getSessionDetail() {
		return sessionDetail;
	}

	public void setSessionDetail(SessionDetail sessionDetail) {
		this.sessionDetail = sessionDetail;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "SessionDetailSubjectTeacher [" + (id != null ? "id=" + id + ", " : "")
				+ (sessionDetail != null ? "sessionDetail=" + sessionDetail.getId() + ", " : "")
				+ (student != null ? "student=" + student.getId() + ", " : "")
				+ "]";
	}

	@Override
	public int compareTo(SessionDetailStudent o) {
		if (this.sessionDetail.getId().equals(o.sessionDetail.getId())) {
			return this.getStudent().getName().compareTo(o.getStudent().getName());
		} else {
			return -1 * this.sessionDetail.getSession().getStartDate().compareTo(o.getSessionDetail().getSession().getStartDate());
		}
	}
}
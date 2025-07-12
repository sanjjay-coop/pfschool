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
@Table(name="tab_session_detail_subject_teacher",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"f_session_detail", "f_subject", "f_teacher"})
    })
public class SessionDetailSubjectTeacher extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4353398688530775836L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@ManyToOne()
    @JoinColumn(name = "f_session_detail", nullable=false)
	private SessionDetail sessionDetail;
	
	@ManyToOne()
    @JoinColumn(name = "f_subject", nullable=false)
	private Subject subject;
	
	@ManyToOne()
    @JoinColumn(name = "f_teacher", nullable=false)
	private Staff teacher;

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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Staff getTeacher() {
		return teacher;
	}

	public void setTeacher(Staff teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "SessionDetailSubjectTeacher [" + (id != null ? "id=" + id + ", " : "")
				+ (sessionDetail != null ? "sessionDetail=" + sessionDetail.getId() + ", " : "")
				+ (subject != null ? "subject=" + subject.getId() + ", " : "") + (teacher != null ? "teacher=" + teacher.getId() : "")
				+ "]";
	}
	
}

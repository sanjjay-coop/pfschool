package org.pf.school.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="tab_academic_session_detail",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"f_academic_session", "f_school_class"})
    })
public class SessionDetail extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 52170582292911275L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@ManyToOne()
    @JoinColumn(name = "f_academic_session", nullable=false)
	private AcademicSession session;
	
	@ManyToOne()
    @JoinColumn(name = "f_school_class", nullable=false)
	private SchoolClass schoolClass;
	
	@ManyToOne()
    @JoinColumn(name = "f_class_teacher", nullable=true)
	private Staff classTeacher;
	
	@Column(name="f_fee", precision=10, scale=2, nullable=true)
	private BigDecimal fee;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public Staff getClassTeacher() {
		return classTeacher;
	}

	public void setClassTeacher(Staff classTeacher) {
		this.classTeacher = classTeacher;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	@Override
	public String toString() {
		return "SessionDetail [" + (id != null ? "id=" + id + ", " : "")
				+ (session != null ? "session=" + session.getTitle() + ", " : "")
				+ (schoolClass != null ? "schoolClass=" + schoolClass.getCode() + ", " : "")
				+ (classTeacher != null ? "classTeacher=" + classTeacher.getStaffId() + ", " : "")
				+ (fee != null ? "fee=" + fee : "") + "]";
	}
}

package org.pf.school.model;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_attendance",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"f_member", "f_date"})
    })
public class Attendance extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1121068265753706088L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@ManyToOne()
    @JoinColumn(name = "f_member", nullable=false)
	private Member member;
	
	@Column(name="f_date", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name="f_status", length=20, nullable=false)
	private String status;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Attendance [" + (id != null ? "id=" + id + ", " : "")
				+ (member != null ? "member=" + member.getUid() + ", " : "") + (date != null ? "date=" + date + ", " : "")
				+ (status != null ? "status=" + status : "") + "]";
	}
	
}

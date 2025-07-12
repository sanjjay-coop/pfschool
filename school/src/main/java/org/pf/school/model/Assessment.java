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
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_assessment",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"f_session", "f_name"})
    })
public class Assessment extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 636208134437582984L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@ManyToOne()
    @JoinColumn(name = "f_session", nullable=false)
	private AcademicSession session;
	
	@Column(name="f_name", length=100, nullable=false)
	private String name;
	
	@Column(name="f_start_date", nullable=true)
	private Date startDate;
	
	@Column(name="f_end_date", nullable=true)
	private Date endDate;

	@Transient 
	private String searchFor;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Assessment [" + (id != null ? "id=" + id + ", " : "")
				+ (session != null ? "session=" + session.getId() + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (endDate != null ? "endDate=" + endDate : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		this.setSearchString(name != null ? "" + name : "");
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		this.setSearchString(name != null ? "" + name : "");
	}
	
}

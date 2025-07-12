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
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_academic_session")
public class AcademicSession extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6841269684045957469L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_title", length=50, nullable=false, unique=true)
	private String title;
	
	@Column(name="f_start_date", nullable=false)
	private Date startDate;
	
	@Column(name="f_end_date", nullable=false)
	private Date endDate;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Override
	public String toString() {
		return "AcademicSession [" + (id != null ? "id=" + id + ", " : "")
				+ (title != null ? "title=" + title + ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (endDate != null ? "endDate=" + endDate : "") + "]";
	}
	
	public String getLabel() {
		return this.getTitle();
	}
}
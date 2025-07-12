package org.pf.school.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_event")
public class Event extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4026051955092136166L;
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_title", length=500, nullable=false)
	private String title;
	
	@Column(columnDefinition = "TEXT", name="f_description", nullable=false)
	private String description;
	
	@Column(name="f_from_date_time", nullable=false)
	private LocalDateTime fromDateTime;
	
	@Column(name="f_to_date_time", nullable=true)
	private LocalDateTime toDateTime;
	
	@Column(name="f_contact_details", length=500, nullable=false)
	private String contactDetails;

	@Transient 
	private String searchFor;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getFromDateTime() {
		return fromDateTime;
	}

	public void setFromDateTime(LocalDateTime fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	public LocalDateTime getToDateTime() {
		return toDateTime;
	}

	public void setToDateTime(LocalDateTime toDateTime) {
		this.toDateTime = toDateTime;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Event [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (fromDateTime != null ? "fromDateTime=" + fromDateTime + ", " : "")
				+ (toDateTime != null ? "toDateTime=" + toDateTime + ", " : "")
				+ (contactDetails != null ? "contactDetails=" + contactDetails : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((title != null ? "title=" + title + ", " : "")
				+ (fromDateTime != null ? "fromDateTime=" + fromDateTime + ", " : "")
				+ (contactDetails != null ? "contactDetails=" + contactDetails : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((title != null ? "title=" + title + ", " : "")
				+ (fromDateTime != null ? "fromDateTime=" + fromDateTime + ", " : "")
				+ (contactDetails != null ? "contactDetails=" + contactDetails : ""));
	}
}

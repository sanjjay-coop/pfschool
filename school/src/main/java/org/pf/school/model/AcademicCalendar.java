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

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_academic_calendar")
public class AcademicCalendar extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3668744930208532531L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@ManyToOne()
    @JoinColumn(name = "f_academic_session", nullable=false)
	private AcademicSession session;

	@Column(name="f_event_date", nullable=false)
	private Date eventDate;
	
	@Column(name="f_event", length=200, nullable=false)
	private String event;
	
	@Column(name="f_color", length=20, nullable=false)
	private String color;

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

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "AcademicCalendar [" + (id != null ? "id=" + id + ", " : "")
				+ (session != null ? "session=" + session.getId() + ", " : "")
				+ (eventDate != null ? "eventDate=" + eventDate + ", " : "")
				+ (event != null ? "event=" + event + ", " : "") + (color != null ? "color=" + color : "") + "]";
	}

	
	
}

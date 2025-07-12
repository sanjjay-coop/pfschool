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
@Table(name="tab_advert")
public class Advert extends BaseObject implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3302490171101432152L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_name", length=50, nullable=false, unique=true)
	private String name;
	
	@Column(name="f_content", length=1000, nullable=false)
	private String content;
	
	@Column(name="f_location", length=20, nullable=false)
	private String location;
	
	@Column(name="f_pub_date", nullable=false)
	private Date pubDate;
	
	@Column(name="f_exp_date", nullable=false)
	private Date expDate;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	@Override
	public String toString() {
		return "Advert [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (content != null ? "content=" + content + ", " : "")
				+ (location != null ? "location=" + location + ", " : "")
				+ (pubDate != null ? "pubDate=" + pubDate + ", " : "") + (expDate != null ? "expDate=" + expDate : "")
				+ "]";
	}
}

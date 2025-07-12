package org.pf.school.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.pf.school.common.BaseObject;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_document")
public class Document extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8253359651929217949L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_title", length=100, nullable=false)
	private String title;
	
	@Column(name="f_pub_end_date", nullable=true)
	@Temporal(TemporalType.DATE)
	private Date pubEndDate;
	
	@Column(name="f_file_name", length=255, nullable=true)
	private String fileName;
	
	@Column(name="f_file_type", length=255, nullable=true)
	private String fileType;
	
	@Lob
    @Column(name = "f_file_data")
	private byte[] fileData;
	
	@Transient
	private MultipartFile file;
	
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

	public Date getPubEndDate() {
		return pubEndDate;
	}

	public void setPubEndDate(Date pubEndDate) {
		this.pubEndDate = pubEndDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Document [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (pubEndDate != null ? "pubEndDate=" + pubEndDate + ", " : "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (fileType != null ? "fileType=" + fileType + ", " : "");
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		this.setSearchString(this.title);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		this.setSearchString(this.title);
	}
	
	
}
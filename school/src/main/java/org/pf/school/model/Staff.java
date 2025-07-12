package org.pf.school.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.pf.school.common.BaseObject;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_staff")
public class Staff extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7986438300114674980L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_staff_id", length=20, unique=true, nullable=false)
	private String staffId;
	
	@Column(name="f_name", length=50, nullable=false)
	private String name;
	
	@Column(name="f_spouse_name", length=50, nullable=true)
	private String spouseName;
	
	@Column(name="f_designation", length=50, nullable=true)
	private String designation;
	
	@Column(name="f_date_of_birth", nullable=false)
	private Date dateOfBirth;
	
	@Column(name="f_residence_address", length=200, nullable=false)
	private String residenceAddress;
	
	@Column(name="f_permanent_address", length=200, nullable=true)
	private String permanentAddress;
	
	@Column(name="f_aadhaar", length=20, nullable=true)
	private String aadhaar;
	
	@Column(name="f_pan", length=20, nullable=true)
	private String pan;
	
	@Column(name="f_phone", length=20, nullable=true)
	private String phone;
	
	@Column(name="f_mobile", length=20, nullable=false)
	private String mobile;
	
	@Column(name="f_email", length=50, nullable=false)
	private String email;
	
	@Column(name="f_remarks", length=500, nullable=true)
	private String remarks;
	
	@ManyToOne()
    @JoinColumn(name = "f_category", nullable=false)
	private Category category;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "f_member", nullable=false)
	private Member member;
	
	@ManyToOne()
    @JoinColumn(name = "f_gender", nullable=false)
	private Gender gender;
	
	@Transient
	private String searchFor;
	
	@Transient
	private String password;
	
	@Transient
	private String role;
	
	@Column(name="f_file_name", length=255, nullable=true)
	private String fileName;
	
	@Column(name="f_file_type", length=255, nullable=true)
	private String fileType;
	
	@Lob
    @Column(name = "f_photo")
	private byte[] fileData;
	
	@Transient
	private MultipartFile file;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	@Override
	public String toString() {
		return "Staff [" + (id != null ? "id=" + id + ", " : "") + (staffId != null ? "staffId=" + staffId + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (spouseName != null ? "spouseName=" + spouseName + ", " : "")
				+ (designation != null ? "designation=" + designation + ", " : "")
				+ (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "")
				+ (residenceAddress != null ? "residenceAddress=" + residenceAddress + ", " : "")
				+ (permanentAddress != null ? "permanentAddress=" + permanentAddress + ", " : "")
				+ (aadhaar != null ? "aadhaar=" + aadhaar + ", " : "") + (pan != null ? "pan=" + pan + ", " : "")
				+ (phone != null ? "phone=" + phone + ", " : "") + (mobile != null ? "mobile=" + mobile + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (remarks != null ? "remarks=" + remarks + ", " : "")
				+ (category != null ? "category=" + category + ", " : "")
				+ (member != null ? "member=" + member + ", " : "") + (gender != null ? "gender=" + gender + ", " : "")
				+ (searchFor != null ? "searchFor=" + searchFor : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		name = name.trim();
		
		this.setSearchString((staffId != null ? "staffId=" + staffId + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (spouseName != null ? "spouseName=" + spouseName + ", " : "")
				+ (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "")
				+ (residenceAddress != null ? "residenceAddress=" + residenceAddress + ", " : "")
				+ (aadhaar != null ? "aadhaar=" + aadhaar + ", " : "") + (pan != null ? "pan=" + pan + ", " : "")
				+ (phone != null ? "phone=" + phone + ", " : "") + (mobile != null ? "mobile=" + mobile + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (remarks != null ? "remarks=" + remarks + ", " : ""));
		
		this.staffId = this.staffId.toUpperCase();
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		name = name.trim();
		
		this.setSearchString((staffId != null ? "staffId=" + staffId + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (spouseName != null ? "spouseName=" + spouseName + ", " : "")
				+ (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "")
				+ (residenceAddress != null ? "residenceAddress=" + residenceAddress + ", " : "")
				+ (aadhaar != null ? "aadhaar=" + aadhaar + ", " : "") + (pan != null ? "pan=" + pan + ", " : "")
				+ (phone != null ? "phone=" + phone + ", " : "") + (mobile != null ? "mobile=" + mobile + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (remarks != null ? "remarks=" + remarks + ", " : ""));
		
		this.staffId = this.staffId.toUpperCase();
	}
	
	public String getLabel() {
		return this.name +
				" : " + this.staffId +
				" : " + this.designation;
	}
}

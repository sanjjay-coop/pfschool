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
@Table(name="tab_student")
public class Student extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -120660492455557681L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_student_id", length=20, unique=true, nullable=false)
	private String studentId;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "f_member", nullable=false)
	private Member member;
	
	@Column(name="f_name", length=100, nullable=false)
	private String name;
	
	@Column(name="f_date_of_birth", nullable=false)
	private Date dateOfBirth;
	
	@Column(name="f_place_of_birth", length=50, nullable=false)
	private String placeOfBirth;
	
	@ManyToOne()
    @JoinColumn(name = "f_category", nullable=false)
	private Category category;
	
	@ManyToOne()
    @JoinColumn(name = "f_gender", nullable=false)
	private Gender gender;
	
	@Column(name="f_name_of_father", length=50, nullable=false)
	private String nameOfFather;
	
	@Column(name="f_name_of_mother", length=50, nullable=false)
	private String nameOfMother;
	
	@Column(name="f_office_address_of_parent", length=255)
	private String officeAddressOfParent;
	
	@Column(name="f_residence_address", length=255, nullable=false)
	private String residenceAddress;
	
	@Column(name="f_blood_group", length=20, nullable=true)
	private String bloodGroup;
	
	@Column(name="f_aadhaar", length=20, nullable=true)
	private String aadhaar;
	
	@Column(name="f_phone_office", length=20, nullable=true)
	private String phoneOffice;
	
	@Column(name="f_phone_residence", length=20, nullable=true)
	private String phoneResidence;
	
	@Column(name="f_mobile", length=20, nullable=false)
	private String mobile;
	
	@Column(name="f_email", length=50, nullable=true)
	private String email;
	
	@Column(name="f_emergency_contact", length=200, nullable=true)
	private String emergencyContact;
	
	@Column(name="f_mother_tongue", length=20, nullable=true)
	private String motherTongue;
	
	@Column(name="f_identification_mark_one", length=100, nullable=true)
	private String identificationMarkOne;
	
	@Column(name="f_identification_mark_two", length=100, nullable=true)
	private String identificationMarkTwo;
	
	@Column(name="f_remarks", length=500, nullable=true)
	private String remarks;
	
	@Column(name="f_date_of_admission", nullable=false)
	private String dateOfAdmission;
	
	@ManyToOne()
    @JoinColumn(name = "f_admission_class", nullable=false)
	private SchoolClass admissionClass;
	
	@Transient
	private String searchFor;
	
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getNameOfFather() {
		return nameOfFather;
	}

	public void setNameOfFather(String nameOfFather) {
		this.nameOfFather = nameOfFather;
	}

	public String getNameOfMother() {
		return nameOfMother;
	}

	public void setNameOfMother(String nameOfMother) {
		this.nameOfMother = nameOfMother;
	}

	public String getOfficeAddressOfParent() {
		return officeAddressOfParent;
	}

	public void setOfficeAddressOfParent(String officeAddressOfParent) {
		this.officeAddressOfParent = officeAddressOfParent;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getPhoneOffice() {
		return phoneOffice;
	}

	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

	public String getPhoneResidence() {
		return phoneResidence;
	}

	public void setPhoneResidence(String phoneResidence) {
		this.phoneResidence = phoneResidence;
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

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getIdentificationMarkOne() {
		return identificationMarkOne;
	}

	public void setIdentificationMarkOne(String identificationMarkOne) {
		this.identificationMarkOne = identificationMarkOne;
	}

	public String getIdentificationMarkTwo() {
		return identificationMarkTwo;
	}

	public void setIdentificationMarkTwo(String identificationMarkTwo) {
		this.identificationMarkTwo = identificationMarkTwo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDateOfAdmission() {
		return dateOfAdmission;
	}

	public void setDateOfAdmission(String dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}

	public SchoolClass getAdmissionClass() {
		return admissionClass;
	}

	public void setAdmissionClass(SchoolClass admissionClass) {
		this.admissionClass = admissionClass;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
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
		return "Student [" + (id != null ? "id=" + id + ", " : "")
				+ (studentId != null ? "studentId=" + studentId + ", " : "")
				+ (member != null ? "member=" + member + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "")
				+ (placeOfBirth != null ? "placeOfBirth=" + placeOfBirth + ", " : "")
				+ (category != null ? "category=" + category + ", " : "")
				+ (gender != null ? "gender=" + gender + ", " : "")
				+ (nameOfFather != null ? "nameOfFather=" + nameOfFather + ", " : "")
				+ (nameOfMother != null ? "nameOfMother=" + nameOfMother + ", " : "")
				+ (officeAddressOfParent != null ? "officeAddressOfParent=" + officeAddressOfParent + ", " : "")
				+ (residenceAddress != null ? "residenceAddress=" + residenceAddress + ", " : "")
				+ (bloodGroup != null ? "bloodGroup=" + bloodGroup + ", " : "")
				+ (aadhaar != null ? "aadhaar=" + aadhaar + ", " : "")
				+ (phoneOffice != null ? "phoneOffice=" + phoneOffice + ", " : "")
				+ (phoneResidence != null ? "phoneResidence=" + phoneResidence + ", " : "")
				+ (mobile != null ? "mobile=" + mobile + ", " : "") + (email != null ? "email=" + email + ", " : "")
				+ (emergencyContact != null ? "emergencyContact=" + emergencyContact + ", " : "")
				+ (motherTongue != null ? "motherTongue=" + motherTongue + ", " : "")
				+ (identificationMarkOne != null ? "identificationMarkOne=" + identificationMarkOne + ", " : "")
				+ (identificationMarkTwo != null ? "identificationMarkTwo=" + identificationMarkTwo + ", " : "")
				+ (remarks != null ? "remarks=" + remarks + ", " : "")
				+ (dateOfAdmission != null ? "dateOfAdmission=" + dateOfAdmission + ", " : "")
				+ (admissionClass != null ? "admissionClass=" + admissionClass + ", " : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		name = name.trim();
		
		this.setSearchString((studentId != null ? "studentId=" + studentId + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "")
				+ (placeOfBirth != null ? "placeOfBirth=" + placeOfBirth + ", " : "")
				+ (nameOfFather != null ? "nameOfFather=" + nameOfFather + ", " : "")
				+ (nameOfMother != null ? "nameOfMother=" + nameOfMother + ", " : "")
				+ (officeAddressOfParent != null ? "officeAddressOfParent=" + officeAddressOfParent + ", " : "")
				+ (residenceAddress != null ? "residenceAddress=" + residenceAddress + ", " : "")
				+ (aadhaar != null ? "aadhaar=" + aadhaar + ", " : "")
				+ (phoneOffice != null ? "phoneOffice=" + phoneOffice + ", " : "")
				+ (phoneResidence != null ? "phoneResidence=" + phoneResidence + ", " : "")
				+ (mobile != null ? "mobile=" + mobile + ", " : "") + (email != null ? "email=" + email + ", " : "")
				+ (emergencyContact != null ? "emergencyContact=" + emergencyContact + ", " : "")
				+ (motherTongue != null ? "motherTongue=" + motherTongue + ", " : ""));
		
		this.studentId = this.studentId.toUpperCase();
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);

		name = name.trim();
		
		this.setSearchString((studentId != null ? "studentId=" + studentId + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "")
				+ (placeOfBirth != null ? "placeOfBirth=" + placeOfBirth + ", " : "")
				+ (nameOfFather != null ? "nameOfFather=" + nameOfFather + ", " : "")
				+ (nameOfMother != null ? "nameOfMother=" + nameOfMother + ", " : "")
				+ (officeAddressOfParent != null ? "officeAddressOfParent=" + officeAddressOfParent + ", " : "")
				+ (residenceAddress != null ? "residenceAddress=" + residenceAddress + ", " : "")
				+ (aadhaar != null ? "aadhaar=" + aadhaar + ", " : "")
				+ (phoneOffice != null ? "phoneOffice=" + phoneOffice + ", " : "")
				+ (phoneResidence != null ? "phoneResidence=" + phoneResidence + ", " : "")
				+ (mobile != null ? "mobile=" + mobile + ", " : "") + (email != null ? "email=" + email + ", " : "")
				+ (emergencyContact != null ? "emergencyContact=" + emergencyContact + ", " : "")
				+ (motherTongue != null ? "motherTongue=" + motherTongue + ", " : ""));
		
		this.studentId = this.studentId.toUpperCase();
	}
	
	public String getLabel() {
		return this.name +
				" : " + this.studentId +
				" : " + this.nameOfFather;
	}
}

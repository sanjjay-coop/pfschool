package org.pf.school.validator.edit;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.Student;
import org.pf.school.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StudentEditValidator extends BaseValidator implements Validator {

	public static final String PNG_MIME_TYPE="image/png";
	public static final long HUNDRED_KB_IN_BYTES = 102400;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Student.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Student obj = (Student) target;
		
		MultipartFile file = obj.getFile();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studentId", "student.studentId.required");
		
		if (obj.getStudentId()!=null){
			if (!this.lengthRange(obj.getStudentId(), 1, 20)){
				errors.rejectValue("studentId", "student.studentId.size");
			} else {
			
				Student o = this.studentRepo.findByStudentIdIgnoreCase(obj.getStudentId());
				if (o!=null && !o.getId().equals(obj.getId())) {
					errors.rejectValue("studentId", "student.studentId.unique");
				}
				
				if (!this.isAlphaNumeric(obj.getStudentId())) {
					errors.rejectValue("studentId", "student.studentId.format");
				}
				
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "student.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "student.name.size");
			}
		}
		
		if (obj.getDateOfBirth()==null) {
			errors.rejectValue("dateOfBirth", "student.dateOfBirth.required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placeOfBirth", "student.placeOfBirth.required");
		
		if (obj.getPlaceOfBirth()!=null){
			if (!this.lengthRange(obj.getPlaceOfBirth(), 1, 50)){
				errors.rejectValue("placeOfBirth", "student.placeOfBirth.size");
			}
		}
		
		if (obj.getCategory()==null) {
			errors.rejectValue("category", "student.category.required");
		}
		
		if (obj.getGender()==null) {
			errors.rejectValue("gender", "student.gender.required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nameOfFather", "student.nameOfFather.required");
		
		if (obj.getNameOfFather()!=null){
			if (!this.lengthRange(obj.getNameOfFather(), 1, 50)){
				errors.rejectValue("nameOfFather", "student.nameOfFather.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nameOfMother", "student.nameOfMother.required");
		
		if (obj.getNameOfMother()!=null){
			if (!this.lengthRange(obj.getNameOfMother(), 1, 50)){
				errors.rejectValue("nameOfMother", "student.nameOfMother.size");
			}
		}		

		if (obj.getOfficeAddressOfParent()!=null){
			if (!this.lengthRange(obj.getOfficeAddressOfParent(), 0, 255)){
				errors.rejectValue("officeAddressOfParent", "student.officeAddressOfParent.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "residenceAddress", "student.residenceAddress.required");
		
		if (obj.getResidenceAddress()!=null){
			if (!this.lengthRange(obj.getResidenceAddress(), 1, 255)){
				errors.rejectValue("residenceAddress", "student.residenceAddress.size");
			}
		}

		if (obj.getBloodGroup()!=null){
			if (!this.lengthRange(obj.getBloodGroup(), 0, 20)){
				errors.rejectValue("bloodGroup", "student.bloodGroup.size");
			}
		}

		if (obj.getAadhaar()!=null){
			if (!this.lengthRange(obj.getAadhaar(), 0, 20)){
				errors.rejectValue("aadhaar", "student.aadhaar.size");
			} else {
				if (obj.getAadhaar().length()>0 && !this.isNumeric(obj.getAadhaar())) {
					errors.rejectValue("aadhaar", "student.aadhaar.format");
				}
			}
		}

		if (obj.getPhoneOffice()!=null){
			if (!this.lengthRange(obj.getPhoneOffice(), 0, 20)){
				errors.rejectValue("phoneOffice", "student.phoneOffice.size");
			} else {
				if (obj.getPhoneOffice().length()>0 && !this.isNumeric(obj.getPhoneOffice())) {
					errors.rejectValue("phoneOffice", "student.phoneOffice.format");
				}
			}
		}

		if (obj.getPhoneResidence()!=null){
			if (!this.lengthRange(obj.getPhoneResidence(), 0, 20)){
				errors.rejectValue("phoneResidence", "student.phoneResidence.size");
			} else {
				if (obj.getPhoneResidence().length()>0 && !this.isNumeric(obj.getPhoneResidence())) {
					errors.rejectValue("phoneResidence", "student.phoneResidence.format");
				}
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "student.mobile.required");
		
		if (obj.getMobile()!=null){
			if (!this.lengthRange(obj.getMobile(), 1, 20)){
				errors.rejectValue("mobile", "student.mobile.size");
			} else {
				if (!this.isNumeric(obj.getMobile())) {
					errors.rejectValue("mobile", "student.mobile.format");
				}
			}
		}
		
		if (obj.getEmail()!=null){
			if (!this.lengthRange(obj.getEmail(), 0, 20)){
				errors.rejectValue("email", "student.email.size");
			} else {
				if (obj.getEmail().length()>0 && !this.isEmail(obj.getEmail())) {
					errors.rejectValue("email", "student.email.format");
				}
			}
		}

		if (obj.getEmergencyContact()!=null){
			if (!this.lengthRange(obj.getEmergencyContact(), 0, 200)){
				errors.rejectValue("emergencyContact", "student.emergencyContact.size");
			} 
		}

		if (obj.getMotherTongue()!=null){
			if (!this.lengthRange(obj.getMotherTongue(), 0, 20)){
				errors.rejectValue("motherTongue", "student.motherTongue.size");
			} 
		}

		if (obj.getIdentificationMarkOne()!=null){
			if (!this.lengthRange(obj.getIdentificationMarkOne(), 0, 100)){
				errors.rejectValue("identificationMarkOne", "student.identificationMarkOne.size");
			} 
		}

		if (obj.getIdentificationMarkTwo()!=null){
			if (!this.lengthRange(obj.getIdentificationMarkTwo(), 0, 100)){
				errors.rejectValue("identificationMarkTwo", "student.identificationMarkTwo.size");
			} 
		}

		if (obj.getRemarks()!=null){
			if (!this.lengthRange(obj.getRemarks(), 0, 500)){
				errors.rejectValue("remarks", "student.remarks.size");
			} 
		}
		
		if (obj.getDateOfAdmission()==null) {
			errors.rejectValue("dateOfAdmission", "student.dateOfAdmission.required");
		}
		
		if (obj.getAdmissionClass()==null) {
			errors.rejectValue("admissionClass", "student.admissionClass.required");
		}
		
		if(file.isEmpty()){
			//errors.rejectValue("file", "fileUploadForm.profile.file.required");
		} else if(!PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
			errors.rejectValue("file", "fileUploadForm.profile.file.invalid.type");
		} else if(file.getSize() > HUNDRED_KB_IN_BYTES){
			errors.rejectValue("file", "fileUploadForm.profile.exceeded.file.size");
		}
	}
}
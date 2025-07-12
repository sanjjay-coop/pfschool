package org.pf.school.validator.edit;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.Staff;
import org.pf.school.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StaffEditValidator extends BaseValidator implements Validator {

	public static final String PNG_MIME_TYPE="image/png";
	public static final long HUNDRED_KB_IN_BYTES = 102400;
	
	@Autowired
	private StaffRepo staffRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Staff.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Staff obj = (Staff) target;
		
		MultipartFile file = obj.getFile();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "staffId", "staff.staffId.required");
		
		if (obj.getStaffId()!=null){
			if (!this.lengthRange(obj.getStaffId(), 1, 20)){
				errors.rejectValue("staffId", "staff.staffId.size");
			} else {
			
				Staff o = this.staffRepo.findByStaffIdIgnoreCase(obj.getStaffId());
				if (o!=null && !o.getId().equals(obj.getId())) {
					errors.rejectValue("staffId", "staff.staffId.unique");
				}
				
				if (!this.isAlphaNumeric(obj.getStaffId())) {
					errors.rejectValue("staffId", "staff.staffId.format");
				}
				
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "staff.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "staff.name.size");
			}
		}
		
		if (obj.getSpouseName()!=null){
			if (!this.lengthRange(obj.getSpouseName(), 0, 50)){
				errors.rejectValue("spouseName", "staff.spouseName.size");
			}
		}
		
		if (obj.getDateOfBirth()==null) {
			errors.rejectValue("dateOfBirth", "staff.dateOfBirth.required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "residenceAddress", "staff.residenceAddress.required");
		
		if (obj.getResidenceAddress()!=null){
			if (!this.lengthRange(obj.getResidenceAddress(), 1, 200)){
				errors.rejectValue("residenceAddress", "staff.residenceAddress.size");
			}
		}

		if (obj.getPermanentAddress()!=null){
			if (!this.lengthRange(obj.getPermanentAddress(), 0, 200)){
				errors.rejectValue("permanentAddress", "staff.permanentAddress.size");
			}
		}

		if (obj.getAadhaar()!=null){
			if (!this.lengthRange(obj.getAadhaar(), 0, 20)){
				errors.rejectValue("aadhaar", "staff.aadhaar.size");
			} else {
				if (obj.getAadhaar().length()>0 && !this.isNumeric(obj.getAadhaar())) {
					errors.rejectValue("aadhaar", "staff.aadhaar.format");
				}
			}
		}

		if (obj.getPan()!=null){
			if (!this.lengthRange(obj.getPan(), 0, 20)){
				errors.rejectValue("pan", "staff.pan.size");
			} else {
				if (obj.getPan().length()>0 && !this.isAlphaNumeric(obj.getPan())) {
					errors.rejectValue("pan", "staff.pan.format");
				}
			}
		}

		if (obj.getPhone()!=null){
			if (!this.lengthRange(obj.getPhone(), 0, 20)){
				errors.rejectValue("phone", "staff.phone.size");
			} else {
				if (obj.getPhone().length()>0 && !this.isNumeric(obj.getPhone())) {
					errors.rejectValue("phone", "staff.phone.format");
				}
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "staff.mobile.required");
		
		if (obj.getMobile()!=null){
			if (!this.lengthRange(obj.getMobile(), 1, 20)){
				errors.rejectValue("mobile", "staff.mobile.size");
			} else {
				if (!this.isNumeric(obj.getMobile())) {
					errors.rejectValue("mobile", "staff.mobile.format");
				}
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "staff.email.required");
		
		if (obj.getEmail()!=null){
			if (!this.lengthRange(obj.getEmail(), 1, 50)){
				errors.rejectValue("email", "staff.email.size");
			} else {
				if (!this.isEmail(obj.getEmail())) {
					errors.rejectValue("email", "staff.email.format");
				}
			}
		}

		if (obj.getRemarks()!=null){
			if (!this.lengthRange(obj.getRemarks(), 0, 500)){
				errors.rejectValue("remarks", "staff.remarks.size");
			} 
		}
		
		if (obj.getGender()==null) {
			errors.rejectValue("gender", "staff.gender.required");
		}
		
		if (obj.getCategory()==null) {
			errors.rejectValue("category", "staff.category.required");
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

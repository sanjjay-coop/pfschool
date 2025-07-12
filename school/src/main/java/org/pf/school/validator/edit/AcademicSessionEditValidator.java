package org.pf.school.validator.edit;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.AcademicSession;
import org.pf.school.repository.AcademicSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AcademicSessionEditValidator extends BaseValidator implements Validator {

	
	@Autowired
	private AcademicSessionRepo academicSessionRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return AcademicSession.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		AcademicSession obj = (AcademicSession) target;
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "academicSession.title.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 50)){
				errors.rejectValue("title", "academicSession.title.size");
			} else {
			
				AcademicSession o = this.academicSessionRepo.findByTitleIgnoreCase(obj.getTitle());
				
				if (o!=null && !o.getId().equals(obj.getId())) {
					errors.rejectValue("title", "academicSession.title.unique");
				}		
			}
		}
		
		if (obj.getStartDate()==null) {
			errors.rejectValue("startDate", "academicSession.startDate.required");
		}
		
		if (obj.getEndDate()==null) {
			errors.rejectValue("endDate", "academicSession.endDate.required");
		}
	}
}

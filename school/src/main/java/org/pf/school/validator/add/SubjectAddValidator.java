package org.pf.school.validator.add;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.Subject;
import org.pf.school.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SubjectAddValidator extends BaseValidator implements Validator {

	@Autowired
	private SubjectRepo subjectRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Subject.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Subject obj = (Subject) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "subject.code.required");
		
		if (obj.getCode()!=null){
			if (!this.lengthRange(obj.getCode(), 1, 20)){
				errors.rejectValue("code", "subject.code.size");
			}
			
			Subject o = this.subjectRepo.findByCodeIgnoreCase(obj.getCode());
			if (o!=null) {
				errors.rejectValue("code", "subject.code.unique");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "subject.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "subject.name.size");
			}
			
			Subject o = this.subjectRepo.findByNameIgnoreCase(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "subject.name.unique");
			}
		}
	}
}

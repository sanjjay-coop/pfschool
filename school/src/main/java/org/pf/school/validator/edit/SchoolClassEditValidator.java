package org.pf.school.validator.edit;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.SchoolClass;
import org.pf.school.repository.SchoolClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SchoolClassEditValidator extends BaseValidator implements Validator {

	@Autowired
	private SchoolClassRepo schoolClassRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return SchoolClass.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SchoolClass obj = (SchoolClass) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "schoolClass.code.required");
		
		if (obj.getCode()!=null){
			if (!this.lengthRange(obj.getCode(), 1, 10)){
				errors.rejectValue("code", "schoolClass.code.size");
			}
			
			SchoolClass o = this.schoolClassRepo.findByCodeIgnoreCase(obj.getCode());
			if (o!=null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("code", "schoolClass.code.unique");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "schoolClass.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "schoolClass.name.size");
			}
			
			SchoolClass o = this.schoolClassRepo.findByNameIgnoreCase(obj.getName());
			if (o!=null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("name", "schoolClass.name.unique");
			}
		}
	}
}

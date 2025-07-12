package org.pf.school.validator.add;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.Gender;
import org.pf.school.repository.GenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GenderAddValidator extends BaseValidator implements Validator {

	@Autowired
	private GenderRepo genderRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Gender.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Gender obj = (Gender) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "gender.code.required");
		
		if (obj.getCode()!=null){
			if (!this.lengthRange(obj.getCode(), 1, 10)){
				errors.rejectValue("code", "gender.code.size");
			}
			
			Gender o = this.genderRepo.findByCodeIgnoreCase(obj.getCode());
			if (o!=null) {
				errors.rejectValue("code", "gender.code.unique");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "gender.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "gender.name.size");
			}
			
			Gender o = this.genderRepo.findByNameIgnoreCase(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "gender.name.unique");
			}
		}
	}
}


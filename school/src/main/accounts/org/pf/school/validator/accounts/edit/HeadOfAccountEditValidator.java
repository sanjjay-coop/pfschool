package org.pf.school.validator.accounts.edit;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.accounts.HeadOfAccount;
import org.pf.school.repository.accounts.HeadOfAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class HeadOfAccountEditValidator extends BaseValidator implements Validator {

	@Autowired
	private HeadOfAccountRepo headOfAccountRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return HeadOfAccount.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		HeadOfAccount obj = (HeadOfAccount) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "headOfAccount.code.required");
		
		if (obj.getCode()!=null){
			if (!this.lengthRange(obj.getCode(), 1, 20)){
				errors.rejectValue("code", "headOfAccount.code.size");
			}
			
			HeadOfAccount o = this.headOfAccountRepo.findByCodeIgnoreCase(obj.getCode());
			
			if (o!=null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("code", "headOfAccount.code.unique");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "headOfAccount.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "headOfAccount.name.size");
			}
			
			HeadOfAccount o = this.headOfAccountRepo.findByNameIgnoreCase(obj.getName());
			if (o!=null && !o.getId().equals(obj.getId())) {
				errors.rejectValue("name", "headOfAccount.name.unique");
			}
		}
	}
}


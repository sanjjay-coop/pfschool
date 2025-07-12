package org.pf.school.validator.accounts;

import org.pf.school.common.BaseValidator;
import org.pf.school.forms.accounts.AccountsSearchForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AccountsSearchFormValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return AccountsSearchForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		AccountsSearchForm obj = (AccountsSearchForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "searchFor", "accountsSearchForm.searchFor.required");
	
		if (obj.getSearchFor()!=null) {
			if (!this.lengthRange(obj.getSearchFor(), 3, 50)) {
				errors.rejectValue("searchFor", "accountsSearchForm.searchFor.size");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "searchIn", "accountsSearchForm.searchIn.required");

	}
}

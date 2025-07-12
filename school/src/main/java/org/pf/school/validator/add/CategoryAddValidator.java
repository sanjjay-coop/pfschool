package org.pf.school.validator.add;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.Category;
import org.pf.school.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CategoryAddValidator extends BaseValidator implements Validator {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Category.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Category obj = (Category) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "category.code.required");
		
		if (obj.getCode()!=null){
			if (!this.lengthRange(obj.getCode(), 1, 10)){
				errors.rejectValue("code", "category.code.size");
			}
			
			Category o = this.categoryRepo.findByCodeIgnoreCase(obj.getCode());
			if (o!=null) {
				errors.rejectValue("code", "category.code.unique");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "category.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 50)){
				errors.rejectValue("name", "category.name.size");
			}
			
			Category o = this.categoryRepo.findByNameIgnoreCase(obj.getName());
			if (o!=null) {
				errors.rejectValue("name", "category.name.unique");
			}
		}
	}
}

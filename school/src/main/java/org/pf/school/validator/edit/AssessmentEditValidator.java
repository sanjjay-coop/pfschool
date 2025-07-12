package org.pf.school.validator.edit;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.Assessment;
import org.pf.school.repository.AssessmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AssessmentEditValidator extends BaseValidator implements Validator {

	@Autowired
	private AssessmentRepo assessmentRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Assessment.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Assessment obj = (Assessment) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "assessment.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "assessment.name.size");
			}
			
			if (obj.getSession()!=null) {
				
				Assessment o = this.assessmentRepo.findBySessionAndNameIgnoreCase(obj.getSession(), obj.getName());
				
				if (o!=null && !o.getId().equals(obj.getId())) {
					errors.rejectValue("name", "assessment.session.name.unique");
				}
			} else {
				errors.rejectValue("session", "assessment.session.required");
			}
		}
	}
}

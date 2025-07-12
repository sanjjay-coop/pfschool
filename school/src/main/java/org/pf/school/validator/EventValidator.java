package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.forms.FileUploadForm;
import org.pf.school.model.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EventValidator extends BaseValidator implements Validator  {

	@Override
	public boolean supports(Class<?> cls) {
		return FileUploadForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Event obj = (Event) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "event.title.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 500)){
				errors.rejectValue("title", "event.title.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "event.description.required");

		if (obj.getFromDateTime()==null){
			errors.rejectValue("fromDateTime", "event.fromDateTime.required");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactDetails", "event.contactDetails.required");

		if (obj.getContactDetails()!=null){
			if (!this.lengthRange(obj.getContactDetails(), 1, 500)){
				errors.rejectValue("contactDetails", "event.contactDetails.size");
			}
		}

	}
}


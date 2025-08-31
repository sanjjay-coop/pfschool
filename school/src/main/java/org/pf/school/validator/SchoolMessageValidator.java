package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.SchoolMessage;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SchoolMessageValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return SchoolMessage.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SchoolMessage obj = (SchoolMessage) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "schoolMessage.subject.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "schoolMessage.content.required");
		
		if (obj.getSubject()!=null){
			if (!this.lengthRange(obj.getSubject(), 1, 100)){
				errors.rejectValue("subject", "schoolMessage.subject.size");
			}
		}

		if (obj.getContent()!=null){
			if (!this.lengthRange(obj.getContent(), 1, 5000)){
				errors.rejectValue("content", "schoolMessage.content.size");
			}
		}
		
		/*
		
		if (obj.getMessageFrom()==null) {
			errors.rejectValue("messageFrom", "schoolMessage.messageFrom.required");
		}
		
		if (obj.getMessageTo()==null) {
			errors.rejectValue("messageTo", "schoolMessage.messageTo.required");
		}
		*/
	}
}


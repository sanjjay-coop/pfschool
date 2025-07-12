package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.AcademicCalendar;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AcademicCalendarValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return AcademicCalendar.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		AcademicCalendar obj = (AcademicCalendar) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "event", "academicCalendar.event.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "color", "academicCalendar.color.required");
		
		if (obj.getEvent()!=null){
			if (!this.lengthRange(obj.getEvent(), 1, 200)){
				errors.rejectValue("event", "academicCalendar.event.size");
			}
		}

		if (obj.getColor()!=null){
			if (!this.lengthRange(obj.getColor(), 1, 20)){
				errors.rejectValue("color", "academicCalendar.color.size");
			}
		}

		if (obj.getEventDate()==null){
			errors.rejectValue("eventDate", "academicCalendar.eventDate.required");
		}

		if (obj.getSession()==null){
			errors.rejectValue("session", "academicCalendar.session.required");
		}
		
		if (obj.getSession()!=null && obj.getEventDate()!=null) {
			if (obj.getEventDate().compareTo(obj.getSession().getStartDate())<0 || obj.getEventDate().compareTo(obj.getSession().getEndDate())>0) {
				errors.rejectValue("eventDate", "academicCalendar.eventDate.between");
			}
		}
	}
}



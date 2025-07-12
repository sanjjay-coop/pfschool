package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.forms.AttendanceForm;
import org.pf.school.forms.FileUploadForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AttendanceFormValidator extends BaseValidator implements Validator  {

	@Override
	public boolean supports(Class<?> cls) {
		return FileUploadForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		AttendanceForm obj = (AttendanceForm) target;
		
		if (obj.getDate()==null){
			errors.rejectValue("date", "attendanceForm.date.required");
		}
	}
}

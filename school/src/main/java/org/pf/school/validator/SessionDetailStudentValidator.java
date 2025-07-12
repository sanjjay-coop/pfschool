package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.forms.FileUploadForm;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.repository.SessionDetailStudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SessionDetailStudentValidator extends BaseValidator implements Validator  {

	@Autowired
	SessionDetailStudentRepo sessionDetailStudentRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return FileUploadForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SessionDetailStudent obj = (SessionDetailStudent) target;
		
		if (obj.getSessionDetail() == null) {
			errors.rejectValue("sessionDetail", "sessionDetailStudent.sessionDetail.required");
		}
		
		if (obj.getStudent() == null) {
			errors.rejectValue("student", "sessionDetailStudent.student.required");
		}
		
		if (obj.getSessionDetail() != null && obj.getStudent() != null) {
			SessionDetailStudent sdt = this.sessionDetailStudentRepo.findBySessionDetailAndStudent(obj.getSessionDetail(), obj.getStudent());
			
			if (sdt != null) {
				errors.rejectValue("sessionDetail", "sessionDetailStudent.sessionDetail.unique");
			}
		}
	}
}

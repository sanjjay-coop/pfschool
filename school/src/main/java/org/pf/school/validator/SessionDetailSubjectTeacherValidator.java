package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.forms.FileUploadForm;
import org.pf.school.model.SessionDetailSubjectTeacher;
import org.pf.school.repository.SessionDetailSubjectTeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SessionDetailSubjectTeacherValidator extends BaseValidator implements Validator  {

	@Autowired
	SessionDetailSubjectTeacherRepo sessionDetailSubjectTeacherRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return FileUploadForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SessionDetailSubjectTeacher obj = (SessionDetailSubjectTeacher) target;
		
		if (obj.getSessionDetail() == null) {
			errors.rejectValue("sessionDetail", "sessionDetailSubjectTeacher.sessionDetail.required");
		}
		
		if (obj.getSubject() == null) {
			errors.rejectValue("subject", "sessionDetailSubjectTeacher.subject.required");
		}
		
		if (obj.getTeacher() == null) {
			errors.rejectValue("teacher", "sessionDetailSubjectTeacher.teacher.required");
		}
		
		if (obj.getSessionDetail() != null && obj.getSubject() != null) {
			SessionDetailSubjectTeacher sdst = this.sessionDetailSubjectTeacherRepo.findBySessionDetailAndSubject(obj.getSessionDetail(), obj.getSubject());
			
			if (sdst != null) {
				errors.rejectValue("subject", "sessionDetailSubjectTeacher.subject.unique");
			}
		}
		
		if (obj.getSessionDetail() != null && obj.getSubject() != null && obj.getTeacher() != null) {
			SessionDetailSubjectTeacher sdst = this.sessionDetailSubjectTeacherRepo.findBySessionDetailAndSubjectAndTeacher(obj.getSessionDetail(), obj.getSubject(), obj.getTeacher());
			
			if (sdst != null) {
				errors.rejectValue("teacher", "sessionDetailSubjectTeacher.teacher.unique");
			}
		}
		
	}
}

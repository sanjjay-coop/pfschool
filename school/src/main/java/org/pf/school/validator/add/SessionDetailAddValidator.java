package org.pf.school.validator.add;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.SessionDetail;
import org.pf.school.repository.SessionDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SessionDetailAddValidator extends BaseValidator implements Validator {

	@Autowired
	private SessionDetailRepo sessionDetailRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return SessionDetail.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SessionDetail obj = (SessionDetail) target;
		
		if (obj.getSession()==null){
			errors.rejectValue("session", "sessionDetail.session.required");
		}
		
		if (obj.getSchoolClass()==null){
			errors.rejectValue("session", "sessionDetail.session.required");
		}

		if (obj.getSchoolClass() != null && obj.getSchoolClass() !=null) {
			SessionDetail o = this.sessionDetailRepo.findBySessionAndSchoolClass(obj.getSession(), obj.getSchoolClass());
			
			if (o!=null) {
				errors.rejectValue("session", "sessionDetail.session.unique");
			}
		}
	}
}

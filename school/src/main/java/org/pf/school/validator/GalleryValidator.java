package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.forms.FileUploadForm;
import org.pf.school.model.Gallery;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GalleryValidator extends BaseValidator implements Validator  {

	@Override
	public boolean supports(Class<?> cls) {
		return FileUploadForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Gallery obj = (Gallery) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "gallery.title.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 500)){
				errors.rejectValue("title", "gallery.title.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "gallery.description.required");
		
		if (obj.getDate()==null) {
			errors.rejectValue("date", "gallery.date.required");
		}
	}
}

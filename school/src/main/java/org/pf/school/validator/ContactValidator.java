package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.forms.FileUploadForm;
import org.pf.school.model.Contact;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ContactValidator extends BaseValidator implements Validator  {

	public static final String PNG_MIME_TYPE="image/png";
	public static final long HUNDRED_KB_IN_BYTES = 102400;
	
	@Override
	public boolean supports(Class<?> cls) {
		return FileUploadForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Contact obj = (Contact) target;
		
		MultipartFile file = obj.getFile();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "contact.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "contact.name.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "post", "contact.post.required");

		if (obj.getPost()!=null){
			if (!this.lengthRange(obj.getPost(), 1, 100)){
				errors.rejectValue("post", "contact.post.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "contact.phone.required");

		if (obj.getPhone()!=null){
			if (!this.lengthRange(obj.getPhone(), 1, 20)){
				errors.rejectValue("phone", "contact.phone.size");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "contact.email.required");

		if (obj.getEmail()!=null){
			if (!this.lengthRange(obj.getEmail(), 1, 50)){
				errors.rejectValue("email", "contact.email.size");
			}
		}
		
		if(file.isEmpty()){
			//errors.rejectValue("file", "fileUploadForm.profile.file.required");
		} else if(!PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
			errors.rejectValue("file", "fileUploadForm.profile.file.invalid.type");
		} else if(file.getSize() > HUNDRED_KB_IN_BYTES){
			errors.rejectValue("file", "fileUploadForm.profile.exceeded.file.size");
		}
	}
}

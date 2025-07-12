package org.pf.school.validator.edit;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.Document;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DocumentEditValidator extends BaseValidator implements Validator  {

	public static final String PNG_MIME_TYPE="image/png";
	public static final String JPG_MIME_TYPE="image/jpg";
	public static final String JPEG_MIME_TYPE="image/jpeg";
	public static final String PDF_MIME_TYPE="application/pdf";
	public static final long SIZE_IN_BYTES = 2097152;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Document.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Document obj = (Document) target;
		
		MultipartFile file = obj.getFile();
		
		ValidationUtils.rejectIfEmpty(errors, "title", "document.title.required");
		
		if (obj.getTitle()!=null) {
			if (!this.lengthRange(obj.getTitle(), 1, 100)) {
				errors.rejectValue("title", "document.title.size");
			}
		}
		
		if (obj.getPubEndDate()==null) {
			errors.rejectValue("pubEndDate", "document.pubEndDate.required");
		}
		
		if(!file.isEmpty()){
			if(!(PDF_MIME_TYPE.equalsIgnoreCase(file.getContentType()))){
				errors.rejectValue("file", "document.file.type");
			} else if(file.getSize() > SIZE_IN_BYTES){
				errors.rejectValue("file", "document.file.size");
			}
		}
	}
}

package org.pf.school.validator.edit;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.Leader;
import org.pf.school.repository.LeaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class LeaderEditValidator extends BaseValidator implements Validator {

	public static final String PNG_MIME_TYPE="image/png";
	public static final String JPEG_MIME_TYPE="image/jpeg";
	public static final String JPG_MIME_TYPE="image/jpg";
	public static final String GIF_MIME_TYPE="image/gif";
	public static final long HUNDRED_KB_IN_BYTES = 102400;
	
	@Autowired
	private LeaderRepo leaderRepo;
	
	@Override
	public boolean supports(Class<?> cls) {
		return Leader.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Leader obj = (Leader) target;
		
		MultipartFile file = obj.getFile();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "leader.name.required");
		
		if (obj.getName()!=null){
			if (!this.lengthRange(obj.getName(), 1, 100)){
				errors.rejectValue("name", "leader.name.size");
			} else {
			
				Leader o = this.leaderRepo.findByNameIgnoreCase(obj.getName());
				if (o!=null && !o.getId().equals(obj.getId())) {
					errors.rejectValue("name", "leader.name.unique");
				}
			}
		}
		

		
		if(file.isEmpty()){
			//errors.rejectValue("file", "fileUploadForm.profile.file.required");
		} else if(!(PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) || 
				JPG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) || 
				JPEG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) || 
				GIF_MIME_TYPE.equalsIgnoreCase(file.getContentType()))){
			errors.rejectValue("file", "leader.file.invalid.type");
		} else if(file.getSize() > HUNDRED_KB_IN_BYTES){
			errors.rejectValue("file", "leader.exceeded.file.size");
		}		
	}
}
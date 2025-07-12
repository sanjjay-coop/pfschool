package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.model.News;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class NewsValidator extends BaseValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> cls) {
		return News.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		News obj = (News) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "news.title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "news.content.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 200)){
				errors.rejectValue("title", "news.title.size");
			}
		}
		
		if (obj.getContent()!=null){
			if (!this.lengthRange(obj.getContent(), 1, 2000)){
				errors.rejectValue("content", "news.content.size");
			}
		}
	}
}

package org.pf.school.validator;

import org.pf.school.common.BaseValidator;
import org.pf.school.forms.FileUploadForm;
import org.pf.school.model.Article;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ArticleValidator extends BaseValidator implements Validator  {

	@Override
	public boolean supports(Class<?> cls) {
		return FileUploadForm.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Article obj = (Article) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "article.title.required");
		
		if (obj.getTitle()!=null){
			if (!this.lengthRange(obj.getTitle(), 1, 500)){
				errors.rejectValue("title", "article.title.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "article.content.required");

		if (obj.getOrgLink()==null){
			errors.rejectValue("orgLink", "article.orgLink.required");
		}

		if (obj.getAuthor()!=null){
			if (!this.lengthRange(obj.getAuthor(), 0, 200)){
				errors.rejectValue("author", "article.author.size");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "linkTitle", "article.linkTitle.required");
		
		if (obj.getLinkTitle()!=null){
			if (!this.lengthRange(obj.getLinkTitle(), 1, 50)){
				errors.rejectValue("linkTitle", "article.linkTitle.size");
			}
		}
	}
}



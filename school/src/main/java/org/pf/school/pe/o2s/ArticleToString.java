package org.pf.school.pe.o2s;

import org.pf.school.model.Article;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleToString implements Converter<Article, String>{

	@Override
	public String convert(Article source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

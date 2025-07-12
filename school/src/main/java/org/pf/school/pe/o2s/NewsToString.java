package org.pf.school.pe.o2s;

import org.pf.school.model.News;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NewsToString implements Converter<News, String>{

	@Override
	public String convert(News source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

package org.pf.school.pe.o2s;

import org.pf.school.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToString implements Converter<Category, String>{

	@Override
	public String convert(Category source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

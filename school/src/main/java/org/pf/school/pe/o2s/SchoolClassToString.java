package org.pf.school.pe.o2s;

import org.pf.school.model.SchoolClass;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SchoolClassToString implements Converter<SchoolClass, String>{

	@Override
	public String convert(SchoolClass source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

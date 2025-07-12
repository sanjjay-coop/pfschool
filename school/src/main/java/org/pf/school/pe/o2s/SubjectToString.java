package org.pf.school.pe.o2s;

import org.pf.school.model.Subject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubjectToString implements Converter<Subject, String>{

	@Override
	public String convert(Subject source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}
package org.pf.school.pe.o2s;

import org.pf.school.model.AcademicSession;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AcademicSessionToString implements Converter<AcademicSession, String>{

	@Override
	public String convert(AcademicSession source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

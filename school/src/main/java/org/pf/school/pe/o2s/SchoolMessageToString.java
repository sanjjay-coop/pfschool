package org.pf.school.pe.o2s;

import org.pf.school.model.SchoolMessage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SchoolMessageToString implements Converter<SchoolMessage, String>{

	@Override
	public String convert(SchoolMessage source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

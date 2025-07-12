package org.pf.school.pe.o2s;

import org.pf.school.model.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenderToString implements Converter<Gender, String>{

	@Override
	public String convert(Gender source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

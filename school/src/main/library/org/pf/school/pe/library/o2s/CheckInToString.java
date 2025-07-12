package org.pf.school.pe.library.o2s;

import org.pf.school.model.library.CheckIn;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CheckInToString implements Converter<CheckIn, String>{

	@Override
	public String convert(CheckIn source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

package org.pf.school.pe.o2s;

import org.pf.school.model.Holiday;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HolidayToString implements Converter<Holiday, String>{

	@Override
	public String convert(Holiday source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

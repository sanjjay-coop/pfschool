package org.pf.school.pe.o2s;

import org.pf.school.model.AcademicCalendar;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AcademicCalendarToString implements Converter<AcademicCalendar, String>{

	@Override
	public String convert(AcademicCalendar source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

package org.pf.school.pe.o2s;

import org.pf.school.model.Attendance;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AttendanceToString implements Converter<Attendance, String>{

	@Override
	public String convert(Attendance source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

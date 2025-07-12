package org.pf.school.pe.o2s;

import org.pf.school.model.SessionDetailStudent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SessionDetailStudentToString implements Converter<SessionDetailStudent, String>{

	@Override
	public String convert(SessionDetailStudent source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

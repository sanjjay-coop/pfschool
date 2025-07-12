package org.pf.school.pe.o2s;

import org.pf.school.model.SessionDetailSubjectTeacher;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SessionDetailSubjectTeacherToString implements Converter<SessionDetailSubjectTeacher, String>{

	@Override
	public String convert(SessionDetailSubjectTeacher source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

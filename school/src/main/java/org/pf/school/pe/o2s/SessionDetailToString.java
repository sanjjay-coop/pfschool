package org.pf.school.pe.o2s;

import org.pf.school.model.SessionDetail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SessionDetailToString implements Converter<SessionDetail, String>{

	@Override
	public String convert(SessionDetail source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

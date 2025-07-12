package org.pf.school.pe.o2s;

import org.pf.school.model.Member;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberToString implements Converter<Member, String>{

	@Override
	public String convert(Member source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

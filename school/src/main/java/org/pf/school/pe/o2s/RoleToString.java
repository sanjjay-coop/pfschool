package org.pf.school.pe.o2s;

import org.pf.school.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleToString implements Converter<Role, String>{

	@Override
	public String convert(Role source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

package org.pf.school.pe.o2s;

import org.pf.school.model.Staff;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StaffToString implements Converter<Staff, String>{

	@Override
	public String convert(Staff source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

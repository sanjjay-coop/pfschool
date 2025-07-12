package org.pf.school.pe.o2s;

import org.pf.school.model.Student;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentToString implements Converter<Student, String>{

	@Override
	public String convert(Student source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

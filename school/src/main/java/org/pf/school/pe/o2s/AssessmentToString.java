package org.pf.school.pe.o2s;

import org.pf.school.model.Assessment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssessmentToString implements Converter<Assessment, String>{

	@Override
	public String convert(Assessment source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

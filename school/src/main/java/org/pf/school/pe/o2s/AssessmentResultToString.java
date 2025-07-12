package org.pf.school.pe.o2s;

import org.pf.school.model.AssessmentResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AssessmentResultToString implements Converter<AssessmentResult, String>{

	@Override
	public String convert(AssessmentResult source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}


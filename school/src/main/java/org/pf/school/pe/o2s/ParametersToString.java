package org.pf.school.pe.o2s;

import org.pf.school.model.Parameters;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ParametersToString implements Converter<Parameters, String>{

	@Override
	public String convert(Parameters source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

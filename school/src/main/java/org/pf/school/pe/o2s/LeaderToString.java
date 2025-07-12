package org.pf.school.pe.o2s;

import org.pf.school.model.Leader;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LeaderToString implements Converter<Leader, String>{

	@Override
	public String convert(Leader source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

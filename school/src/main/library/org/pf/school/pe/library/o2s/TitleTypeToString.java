package org.pf.school.pe.library.o2s;

import org.pf.school.model.library.TitleType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TitleTypeToString implements Converter<TitleType, String>{

	@Override
	public String convert(TitleType source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

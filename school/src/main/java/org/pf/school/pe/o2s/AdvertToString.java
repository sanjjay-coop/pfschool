package org.pf.school.pe.o2s;

import org.pf.school.model.Advert;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AdvertToString implements Converter<Advert, String>{

	@Override
	public String convert(Advert source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

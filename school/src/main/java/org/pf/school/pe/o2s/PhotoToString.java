package org.pf.school.pe.o2s;

import org.pf.school.model.Photo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PhotoToString implements Converter<Photo, String>{

	@Override
	public String convert(Photo source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}


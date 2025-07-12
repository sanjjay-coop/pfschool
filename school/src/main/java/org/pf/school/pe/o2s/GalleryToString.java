package org.pf.school.pe.o2s;

import org.pf.school.model.Gallery;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GalleryToString implements Converter<Gallery, String>{

	@Override
	public String convert(Gallery source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}


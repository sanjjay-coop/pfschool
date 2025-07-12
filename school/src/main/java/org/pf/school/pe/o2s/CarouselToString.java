package org.pf.school.pe.o2s;

import org.pf.school.model.Carousel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarouselToString implements Converter<Carousel, String>{

	@Override
	public String convert(Carousel source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

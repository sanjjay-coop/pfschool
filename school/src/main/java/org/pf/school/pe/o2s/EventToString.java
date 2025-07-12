package org.pf.school.pe.o2s;

import org.pf.school.model.Event;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EventToString implements Converter<Event, String>{

	@Override
	public String convert(Event source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

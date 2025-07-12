package org.pf.school.pe.o2s;

import org.pf.school.model.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DocumentToString implements Converter<Document, String>{

	@Override
	public String convert(Document source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

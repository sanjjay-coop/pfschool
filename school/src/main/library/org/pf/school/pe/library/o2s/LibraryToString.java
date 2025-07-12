package org.pf.school.pe.library.o2s;

import org.pf.school.model.library.Library;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LibraryToString implements Converter<Library, String>{

	@Override
	public String convert(Library source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

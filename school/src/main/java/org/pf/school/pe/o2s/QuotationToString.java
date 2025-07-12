package org.pf.school.pe.o2s;

import org.pf.school.model.Quotation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class QuotationToString implements Converter<Quotation, String>{

	@Override
	public String convert(Quotation source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

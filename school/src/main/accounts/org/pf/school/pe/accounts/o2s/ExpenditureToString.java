package org.pf.school.pe.accounts.o2s;

import org.pf.school.model.accounts.Expenditure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExpenditureToString implements Converter<Expenditure, String>{

	@Override
	public String convert(Expenditure source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

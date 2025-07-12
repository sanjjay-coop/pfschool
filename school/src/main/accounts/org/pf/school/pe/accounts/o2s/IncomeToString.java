package org.pf.school.pe.accounts.o2s;

import org.pf.school.model.accounts.Income;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IncomeToString implements Converter<Income, String>{

	@Override
	public String convert(Income source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

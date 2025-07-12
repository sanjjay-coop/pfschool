package org.pf.school.pe.accounts.s2o;

import java.util.Optional;

import org.pf.school.model.accounts.Income;
import org.pf.school.repository.accounts.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIncome implements Converter<String, Income>{

	@Autowired
	private IncomeRepo repo;
	
	@Override
	public Income convert(String source) {
		try {
			Optional<Income> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

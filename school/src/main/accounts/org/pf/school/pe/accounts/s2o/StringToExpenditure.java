package org.pf.school.pe.accounts.s2o;

import java.util.Optional;

import org.pf.school.model.accounts.Expenditure;
import org.pf.school.repository.accounts.ExpenditureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToExpenditure implements Converter<String, Expenditure>{

	@Autowired
	private ExpenditureRepo repo;
	
	@Override
	public Expenditure convert(String source) {
		try {
			Optional<Expenditure> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}


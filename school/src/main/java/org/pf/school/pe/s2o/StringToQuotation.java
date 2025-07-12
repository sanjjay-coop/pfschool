package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.Quotation;
import org.pf.school.repository.QuotationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToQuotation implements Converter<String, Quotation>{

	@Autowired
	private QuotationRepo repo;
	
	@Override
	public Quotation convert(String source) {
		try {
			Optional<Quotation> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

package org.pf.school.pe.library.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.library.CheckOut;
import org.pf.school.repository.library.CheckOutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCheckOut implements Converter<String, CheckOut>{

	@Autowired
	private CheckOutRepo repo;
	
	@Override
	public CheckOut convert(String source) {
		try {
			Optional<CheckOut> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

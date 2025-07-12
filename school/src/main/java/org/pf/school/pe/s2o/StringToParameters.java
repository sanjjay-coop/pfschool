package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.Parameters;
import org.pf.school.repository.ParametersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToParameters implements Converter<String, Parameters>{

	@Autowired
	private ParametersRepo repo;
	
	@Override
	public Parameters convert(String source) {
		try {
			Optional<Parameters> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.SchoolMessage;
import org.pf.school.repository.SchoolMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSchoolMessage implements Converter<String, SchoolMessage>{

	@Autowired
	private SchoolMessageRepo repo;
	
	@Override
	public SchoolMessage convert(String source) {
		try {
			Optional<SchoolMessage> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.SchoolClass;
import org.pf.school.repository.SchoolClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSchoolClass implements Converter<String, SchoolClass>{

	@Autowired
	private SchoolClassRepo repo;
	
	@Override
	public SchoolClass convert(String source) {
		try {
			Optional<SchoolClass> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

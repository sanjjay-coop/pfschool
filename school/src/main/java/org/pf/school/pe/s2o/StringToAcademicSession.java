package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.AcademicSession;
import org.pf.school.repository.AcademicSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAcademicSession implements Converter<String, AcademicSession>{

	@Autowired
	private AcademicSessionRepo repo;
	
	@Override
	public AcademicSession convert(String source) {
		try {
			Optional<AcademicSession> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

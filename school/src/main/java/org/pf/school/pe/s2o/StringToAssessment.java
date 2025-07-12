package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.Assessment;
import org.pf.school.repository.AssessmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAssessment implements Converter<String, Assessment>{

	@Autowired
	private AssessmentRepo repo;
	
	@Override
	public Assessment convert(String source) {
		try {
			Optional<Assessment> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

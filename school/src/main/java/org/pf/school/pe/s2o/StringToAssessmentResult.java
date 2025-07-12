package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.AssessmentResult;
import org.pf.school.repository.AssessmentResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAssessmentResult implements Converter<String, AssessmentResult>{

	@Autowired
	private AssessmentResultRepo repo;
	
	@Override
	public AssessmentResult convert(String source) {
		try {
			Optional<AssessmentResult> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.SessionDetailStudent;
import org.pf.school.repository.SessionDetailStudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSessionDetailStudent implements Converter<String, SessionDetailStudent>{

	@Autowired
	private SessionDetailStudentRepo repo;
	
	@Override
	public SessionDetailStudent convert(String source) {
		try {
			Optional<SessionDetailStudent> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

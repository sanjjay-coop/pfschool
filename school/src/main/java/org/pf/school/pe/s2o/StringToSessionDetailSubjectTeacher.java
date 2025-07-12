package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.SessionDetailSubjectTeacher;
import org.pf.school.repository.SessionDetailSubjectTeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSessionDetailSubjectTeacher implements Converter<String, SessionDetailSubjectTeacher>{

	@Autowired
	private SessionDetailSubjectTeacherRepo repo;
	
	@Override
	public SessionDetailSubjectTeacher convert(String source) {
		try {
			Optional<SessionDetailSubjectTeacher> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

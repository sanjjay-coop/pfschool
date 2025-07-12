package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.SessionDetail;
import org.pf.school.repository.SessionDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSessionDetail implements Converter<String, SessionDetail>{

	@Autowired
	private SessionDetailRepo repo;
	
	@Override
	public SessionDetail convert(String source) {
		try {
			Optional<SessionDetail> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

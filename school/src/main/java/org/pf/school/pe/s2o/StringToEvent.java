package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.Event;
import org.pf.school.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEvent implements Converter<String, Event>{

	@Autowired
	private EventRepo repo;
	
	@Override
	public Event convert(String source) {
		try {
			Optional<Event> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}


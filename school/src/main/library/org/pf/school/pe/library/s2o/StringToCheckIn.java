package org.pf.school.pe.library.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.library.CheckIn;
import org.pf.school.repository.library.CheckInRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCheckIn implements Converter<String, CheckIn>{

	@Autowired
	private CheckInRepo repo;
	
	@Override
	public CheckIn convert(String source) {
		try {
			Optional<CheckIn> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

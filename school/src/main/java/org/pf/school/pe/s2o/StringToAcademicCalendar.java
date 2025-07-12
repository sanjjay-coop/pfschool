package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.AcademicCalendar;
import org.pf.school.repository.AcademicCalendarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAcademicCalendar implements Converter<String, AcademicCalendar>{

	@Autowired
	private AcademicCalendarRepo repo;
	
	@Override
	public AcademicCalendar convert(String source) {
		try {
			Optional<AcademicCalendar> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
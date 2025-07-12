package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.Attendance;
import org.pf.school.repository.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAttendance implements Converter<String, Attendance>{

	@Autowired
	private AttendanceRepo repo;
	
	@Override
	public Attendance convert(String source) {
		try {
			Optional<Attendance> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

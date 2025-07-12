package org.pf.school.pe.library.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.library.Title;
import org.pf.school.repository.library.TitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTitle implements Converter<String, Title>{

	@Autowired
	private TitleRepo repo;
	
	@Override
	public Title convert(String source) {
		try {
			Optional<Title> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

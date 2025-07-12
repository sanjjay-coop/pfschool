package org.pf.school.pe.library.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.library.TitleType;
import org.pf.school.repository.library.TitleTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTitleType implements Converter<String, TitleType>{

	@Autowired
	private TitleTypeRepo repo;
	
	@Override
	public TitleType convert(String source) {
		try {
			Optional<TitleType> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

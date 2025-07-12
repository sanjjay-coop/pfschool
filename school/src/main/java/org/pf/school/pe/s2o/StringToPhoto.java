package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.Photo;
import org.pf.school.repository.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPhoto implements Converter<String, Photo>{

	@Autowired
	private PhotoRepo repo;
	
	@Override
	public Photo convert(String source) {
		try {
			Optional<Photo> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

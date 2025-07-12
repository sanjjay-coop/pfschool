package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.Gallery;
import org.pf.school.repository.GalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGallery implements Converter<String, Gallery>{

	@Autowired
	private GalleryRepo repo;
	
	@Override
	public Gallery convert(String source) {
		try {
			Optional<Gallery> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

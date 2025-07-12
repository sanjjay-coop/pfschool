package org.pf.school.pe.library.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.library.Library;
import org.pf.school.repository.library.LibraryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLibrary implements Converter<String, Library>{

	@Autowired
	private LibraryRepo repo;
	
	@Override
	public Library convert(String source) {
		try {
			Optional<Library> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

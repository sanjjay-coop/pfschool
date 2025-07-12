package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.Category;
import org.pf.school.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategory implements Converter<String, Category>{

	@Autowired
	private CategoryRepo repo;
	
	@Override
	public Category convert(String source) {
		try {
			Optional<Category> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

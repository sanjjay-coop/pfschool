package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.Carousel;
import org.pf.school.repository.CarouselRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCarousel implements Converter<String, Carousel>{

	@Autowired
	private CarouselRepo repo;
	
	@Override
	public Carousel convert(String source) {	
		try {
			Optional<Carousel> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

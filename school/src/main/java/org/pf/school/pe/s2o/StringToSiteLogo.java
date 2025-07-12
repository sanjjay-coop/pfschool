package org.pf.school.pe.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.SiteLogo;
import org.pf.school.repository.SiteLogoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSiteLogo implements Converter<String, SiteLogo>{

	@Autowired
	private SiteLogoRepo repo;
	
	@Override
	public SiteLogo convert(String source) {
		try {
			Optional<SiteLogo> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

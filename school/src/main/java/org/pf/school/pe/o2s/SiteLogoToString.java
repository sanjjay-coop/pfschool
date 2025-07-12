package org.pf.school.pe.o2s;

import org.pf.school.model.SiteLogo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SiteLogoToString implements Converter<SiteLogo, String>{

	@Override
	public String convert(SiteLogo source) {
		if (source !=null) return source.getId().toString();
		else return "";
	}
}

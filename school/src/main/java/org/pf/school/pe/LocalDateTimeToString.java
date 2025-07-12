package org.pf.school.pe;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeToString implements Converter<LocalDateTime, String> {

	@Override
	public String convert(LocalDateTime date) {
		final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

		String formattedDateTime = date.format(ISO_FORMATTER);
		//System.out.println(formattedDateTime);
		//System.out.println(formattedDateTime.substring(0, 16));
		return formattedDateTime.substring(0, 16);
	}
}


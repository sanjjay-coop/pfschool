package org.pf.school.pe.accounts.s2o;

import java.util.Optional;
import java.util.UUID;

import org.pf.school.model.accounts.HeadOfAccount;
import org.pf.school.repository.accounts.HeadOfAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToHeadOfAccount implements Converter<String, HeadOfAccount>{

	@Autowired
	private HeadOfAccountRepo repo;
	
	@Override
	public HeadOfAccount convert(String source) {
		try {
			Optional<HeadOfAccount> optionalEntity = repo.findById(UUID.fromString(source));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}

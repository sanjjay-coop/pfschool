package org.pf.school.repository.library;

import java.util.UUID;

import org.pf.school.model.library.TitleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleTypeRepo extends JpaRepository<TitleType, UUID>{

	public TitleType findByName(String name);
}

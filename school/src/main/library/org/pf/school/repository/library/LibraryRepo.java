package org.pf.school.repository.library;

import java.util.UUID;

import org.pf.school.model.library.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepo extends JpaRepository<Library, UUID>{

	public Library findByShortName(String shortName);
	public Library findByName(String name);
}

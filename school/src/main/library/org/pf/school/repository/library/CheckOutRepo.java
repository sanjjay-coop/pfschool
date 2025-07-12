package org.pf.school.repository.library;

import java.util.UUID;

import org.pf.school.model.library.CheckOut;
import org.pf.school.model.library.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckOutRepo extends JpaRepository<CheckOut, UUID>{

	public CheckOut findByTitle(Title title);
}

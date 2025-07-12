package org.pf.school.repository.library;

import java.util.UUID;

import org.pf.school.model.library.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepo extends JpaRepository<CheckIn, UUID>{

	
}

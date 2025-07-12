package org.pf.school.repository;

import java.util.Date;
import java.util.UUID;

import org.pf.school.model.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepo extends JpaRepository<Holiday, UUID>{

	Page<Holiday> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);

	Holiday findByDate(Date date);
}

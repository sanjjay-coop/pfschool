package org.pf.school.repository;

import java.util.UUID;

import org.pf.school.model.Parameters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParametersRepo extends JpaRepository<Parameters, UUID>{

}
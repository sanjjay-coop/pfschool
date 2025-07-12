package org.pf.school.repository.accounts;

import java.util.UUID;

import org.pf.school.model.accounts.HeadOfAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeadOfAccountRepo extends JpaRepository<HeadOfAccount, UUID>{

	HeadOfAccount findByCodeIgnoreCase(String code);
	HeadOfAccount findByNameIgnoreCase(String name);
}

package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.Member;
import org.pf.school.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, UUID>{
	
	Member findByUid(String uid);
	Member findByUidIgnoreCase(String uid);
	
	Page<Member> findByUidContainingIgnoreCase(String uid, Pageable pageable);
	
	Page<Member> findBySearchStringContainingIgnoreCase(String uid, Pageable pageable);
	
	List<Member> findByRoles(Role role);
}

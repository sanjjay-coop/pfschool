package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.Member;
import org.pf.school.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepo extends JpaRepository<Staff, UUID>{
	
	Staff findByStaffIdIgnoreCase(String staffId);
	
	Page<Staff> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	public long countByMember_Enabled(Boolean enabled);
	
	Staff findByMember_Uid(String uid);	
	
	List<Staff> findByMemberInOrderByNameAsc(List<Member> listMember);
	
	Staff findByMember(Member member);
}
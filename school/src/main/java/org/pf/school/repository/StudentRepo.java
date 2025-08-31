package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.Member;
import org.pf.school.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, UUID>{
	
	Student findByStudentIdIgnoreCase(String studentId);
	
	Page<Student> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	public long countByMember_Enabled(Boolean enabled);
	
	Student findByMember_Uid(String uid);
	
	List<Student> findByMemberInOrderByNameAsc(List<Member> listMember);
	
	Student findByMember(Member member);
	
	List<Student> findByMember_Enabled(Boolean enabled);
	
	List<Student> findByIdNotInOrderByNameAsc(List<UUID> listStudent);
	
}
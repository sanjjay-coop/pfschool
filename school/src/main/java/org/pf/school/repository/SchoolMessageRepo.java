package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.Member;
import org.pf.school.model.SchoolMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolMessageRepo extends JpaRepository<SchoolMessage, UUID>{
	
	List<SchoolMessage> findByMessageToOrderByRecordAddDateDesc(Member member);
	
}

package org.pf.school.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.pf.school.model.Attendance;
import org.pf.school.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<Attendance, UUID>{
	
	public List<Attendance> findAllByMemberOrderByDateAsc(Member member);
	
	public Attendance findByDateAndMember(Date date, Member member);
}

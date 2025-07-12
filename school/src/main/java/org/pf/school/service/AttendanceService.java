package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Attendance;
import org.pf.school.model.Audit;
import org.pf.school.repository.AttendanceRepo;
import org.pf.school.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private AttendanceRepo attendanceRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Attendance> oe = this.attendanceRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addAttendance(Attendance obj, String updateBy) {
		
		obj = attendanceRepo.save(obj);
		
		audit = new Audit(updateBy, "Attendance", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteAttendance(UUID id, String updateBy) {

		Optional<Attendance> oe = this.attendanceRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Attendance obj = oe.get();
		
		audit = new Audit(updateBy, "Attendance", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		attendanceRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateAttendance(Attendance attendance, String updateBy) {

		Attendance obj = this.attendanceRepo.findByDateAndMember(attendance.getDate(), attendance.getMember());
		
		if (obj == null) {
			
			attendance.setAddDefaults(updateBy);
			attendance = attendanceRepo.save(attendance);
			
			audit = new Audit(updateBy, "Attendance", attendance.toString(), attendance.getId(), Calendar.getInstance().getTime(), "ADD");
			auditRepo.save(audit);
			
			return new TransactionResult(attendance, true);
			
		} else {
			
			obj.setMember(attendance.getMember());
			obj.setDate(attendance.getDate());
			obj.setStatus(attendance.getStatus());
			
			obj.setAddDefaults(updateBy);
			
			obj = attendanceRepo.save(obj);
			
			audit = new Audit(updateBy, "Attendance", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
			auditRepo.save(audit);
			
			return new TransactionResult(obj, true);
		}
	}
}


package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.AcademicCalendar;
import org.pf.school.model.Audit;
import org.pf.school.repository.AcademicCalendarRepo;
import org.pf.school.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AcademicCalendarService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private AcademicCalendarRepo academicCalendarRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<AcademicCalendar> oe = this.academicCalendarRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addAcademicCalendar(AcademicCalendar obj, String updateBy) {
		
		obj = academicCalendarRepo.save(obj);
		
		audit = new Audit(updateBy, "AcademicCalendar", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteAcademicCalendar(UUID id, String updateBy) {

		Optional<AcademicCalendar> oe = this.academicCalendarRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		AcademicCalendar obj = oe.get();
		
		audit = new Audit(updateBy, "AcademicCalendar", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		academicCalendarRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateAcademicCalendar(AcademicCalendar academicCalendar, String updateBy) {
		
		Optional<AcademicCalendar> oe = this.academicCalendarRepo.findById(academicCalendar.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		AcademicCalendar obj = oe.get();
		
		obj.setSession(academicCalendar.getSession());
		obj.setEvent(academicCalendar.getEvent());
		obj.setEventDate(academicCalendar.getEventDate());
		obj.setColor(academicCalendar.getColor());
		
		obj = academicCalendarRepo.save(obj);
		
		audit = new Audit(updateBy, "AcademicCalendar", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

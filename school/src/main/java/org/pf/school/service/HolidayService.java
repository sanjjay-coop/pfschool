package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Holiday;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.HolidayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class HolidayService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private HolidayRepo holidayRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Holiday> oe = this.holidayRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addHoliday(Holiday obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = holidayRepo.save(obj);
		
		audit = new Audit(updateBy, "Holiday", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteHoliday(UUID id, String updateBy) {

		Optional<Holiday> oe = this.holidayRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Holiday obj = oe.get();
		
		audit = new Audit(updateBy, "Holiday", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		holidayRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
}


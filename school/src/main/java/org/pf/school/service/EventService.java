package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Event;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EventService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private EventRepo eventRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Event> oe = this.eventRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addEvent(Event obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = eventRepo.save(obj);
		
		audit = new Audit(updateBy, "Event", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteEvent(UUID id, String updateBy) {

		Optional<Event> oe = this.eventRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Event obj = oe.get();
		
		audit = new Audit(updateBy, "Event", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		eventRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateEvent(Event event, String updateBy) {
		
		Optional<Event> oe = this.eventRepo.findById(event.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Event obj = oe.get();
		
		obj.setTitle(event.getTitle());
		obj.setDescription(event.getDescription());
		obj.setFromDateTime(event.getFromDateTime());
		obj.setToDateTime(event.getToDateTime());
		obj.setContactDetails(event.getContactDetails());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = eventRepo.save(obj);
		
		audit = new Audit(updateBy, "Event", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

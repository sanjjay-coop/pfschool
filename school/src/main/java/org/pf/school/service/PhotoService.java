package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Photo;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PhotoService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private PhotoRepo photoRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Photo> oe = this.photoRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addPhoto(Photo obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = photoRepo.save(obj);
		
		audit = new Audit(updateBy, "Photo", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deletePhoto(UUID id, String updateBy) {

		Optional<Photo> oe = this.photoRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Photo obj = oe.get();
		
		audit = new Audit(updateBy, "Photo", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		photoRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updatePhoto(Photo photo, String updateBy) {
		
		Optional<Photo> oe = this.photoRepo.findById(photo.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Photo obj = oe.get();
		
		obj.setUpdateDefaults(updateBy);
		
		obj = photoRepo.save(obj);
		
		audit = new Audit(updateBy, "Photo", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

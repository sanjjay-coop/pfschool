package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Gallery;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.GalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class GalleryService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private GalleryRepo galleryRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Gallery> oe = this.galleryRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addGallery(Gallery obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = galleryRepo.save(obj);
		
		audit = new Audit(updateBy, "Gallery", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteGallery(UUID id, String updateBy) {

		Optional<Gallery> oe = this.galleryRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Gallery obj = oe.get();
		
		audit = new Audit(updateBy, "Gallery", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		galleryRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateGallery(Gallery gallery, String updateBy) {
		
		Optional<Gallery> oe = this.galleryRepo.findById(gallery.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Gallery obj = oe.get();
		
		obj.setDescription(gallery.getDescription());
		obj.setTitle(gallery.getTitle());
		obj.setDate(gallery.getDate());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = galleryRepo.save(obj);
		
		audit = new Audit(updateBy, "Gallery", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

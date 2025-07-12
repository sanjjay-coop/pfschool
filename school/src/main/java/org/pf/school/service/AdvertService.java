package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Advert;
import org.pf.school.model.Audit;
import org.pf.school.repository.AdvertRepo;
import org.pf.school.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AdvertService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private AdvertRepo advertRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Advert> oe = this.advertRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addAdvert(Advert obj, String updateBy) {
		
		obj = advertRepo.save(obj);
		
		audit = new Audit(updateBy, "Advert", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteAdvert(UUID id, String updateBy) {

		Optional<Advert> oe = this.advertRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Advert obj = oe.get();
		
		audit = new Audit(updateBy, "Advert", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		advertRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateAdvert(Advert advert, String updateBy) {
		
		Optional<Advert> oe = this.advertRepo.findById(advert.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Advert obj = oe.get();
		
		obj.setName(advert.getName());
		obj.setLocation(advert.getLocation());
		obj.setContent(advert.getContent());
		obj.setExpDate(advert.getExpDate());
		obj.setPubDate(advert.getPubDate());
		
		obj = advertRepo.save(obj);
		
		audit = new Audit(updateBy, "Advert", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

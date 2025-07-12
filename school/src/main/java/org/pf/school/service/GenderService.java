package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Gender;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.GenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class GenderService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private GenderRepo genderRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Gender> oe = this.genderRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addGender(Gender obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = genderRepo.save(obj);
		
		audit = new Audit(updateBy, "Gender", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteGender(UUID id, String updateBy) {

		Optional<Gender> oe = this.genderRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Gender obj = oe.get();
		
		audit = new Audit(updateBy, "Gender", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		genderRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateGender(Gender gender, String updateBy) {
		
		Optional<Gender> oe = this.genderRepo.findById(gender.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Gender obj = oe.get();
		
		obj.setCode(gender.getCode());
		obj.setName(gender.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = genderRepo.save(obj);
		
		audit = new Audit(updateBy, "Gender", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

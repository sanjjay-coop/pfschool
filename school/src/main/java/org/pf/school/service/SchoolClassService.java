package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.SchoolClass;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.SchoolClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SchoolClassService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private SchoolClassRepo schoolClassRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<SchoolClass> oe = this.schoolClassRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addSchoolClass(SchoolClass obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = schoolClassRepo.save(obj);
		
		audit = new Audit(updateBy, "SchoolClass", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSchoolClass(UUID id, String updateBy) {

		Optional<SchoolClass> oe = this.schoolClassRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		SchoolClass obj = oe.get();
		
		audit = new Audit(updateBy, "SchoolClass", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		schoolClassRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSchoolClass(SchoolClass schoolClass, String updateBy) {
		
		Optional<SchoolClass> oe = this.schoolClassRepo.findById(schoolClass.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		SchoolClass obj = oe.get();
		
		obj.setCode(schoolClass.getCode());
		obj.setName(schoolClass.getName());
		obj.setSeqNumber(schoolClass.getSeqNumber());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = schoolClassRepo.save(obj);
		
		audit = new Audit(updateBy, "SchoolClass", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

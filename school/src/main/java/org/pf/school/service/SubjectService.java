package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Subject;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SubjectService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private SubjectRepo subjectRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Subject> oe = this.subjectRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addSubject(Subject obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = subjectRepo.save(obj);
		
		audit = new Audit(updateBy, "Subject", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSubject(UUID id, String updateBy) {

		Optional<Subject> oe = this.subjectRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Subject obj = oe.get();
		
		audit = new Audit(updateBy, "Subject", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		subjectRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSubject(Subject subject, String updateBy) {
		
		Optional<Subject> oe = this.subjectRepo.findById(subject.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Subject obj = oe.get();
		
		obj.setCode(subject.getCode());
		obj.setName(subject.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = subjectRepo.save(obj);
		
		audit = new Audit(updateBy, "Subject", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}



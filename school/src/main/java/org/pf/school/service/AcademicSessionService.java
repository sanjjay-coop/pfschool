package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.AcademicSession;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.AcademicSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AcademicSessionService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private AcademicSessionRepo academicSessionRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<AcademicSession> oe = this.academicSessionRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addAcademicSession(AcademicSession obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = academicSessionRepo.save(obj);
		
		audit = new Audit(updateBy, "AcademicSession", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteAcademicSession(UUID id, String updateBy) {

		Optional<AcademicSession> oe = this.academicSessionRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		AcademicSession obj = oe.get();
		
		audit = new Audit(updateBy, "AcademicSession", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		academicSessionRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateAcademicSession(AcademicSession academicSession, String updateBy) {
		
		Optional<AcademicSession> oe = this.academicSessionRepo.findById(academicSession.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		AcademicSession obj = oe.get();
		
		obj.setTitle(academicSession.getTitle());
		obj.setStartDate(academicSession.getStartDate());
		obj.setEndDate(academicSession.getEndDate());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = academicSessionRepo.save(obj);
		
		audit = new Audit(updateBy, "AcademicSession", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

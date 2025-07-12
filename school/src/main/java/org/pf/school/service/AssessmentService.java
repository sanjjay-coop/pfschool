package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Assessment;
import org.pf.school.model.Audit;
import org.pf.school.repository.AssessmentRepo;
import org.pf.school.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AssessmentService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private AssessmentRepo assessmentRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Assessment> oe = this.assessmentRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addAssessment(Assessment obj, String updateBy) {
		
		obj = assessmentRepo.save(obj);
		
		audit = new Audit(updateBy, "Assessment", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteAssessment(UUID id, String updateBy) {

		Optional<Assessment> oe = this.assessmentRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Assessment obj = oe.get();
		
		audit = new Audit(updateBy, "Assessment", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		assessmentRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateAssessment(Assessment assessment, String updateBy) {
		
		Optional<Assessment> oe = this.assessmentRepo.findById(assessment.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Assessment obj = oe.get();
		
		obj.setName(assessment.getName());
		obj.setSession(assessment.getSession());
		obj.setStartDate(assessment.getStartDate());
		obj.setEndDate(assessment.getEndDate());
		
		obj = assessmentRepo.save(obj);
		
		audit = new Audit(updateBy, "Assessment", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

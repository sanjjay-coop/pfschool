package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.AssessmentResult;
import org.pf.school.model.Audit;
import org.pf.school.repository.AssessmentResultRepo;
import org.pf.school.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AssessmentResultService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private AssessmentResultRepo assessmentResultRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<AssessmentResult> oe = this.assessmentResultRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addAssessmentResult(AssessmentResult obj, String updateBy) {
		
		obj = assessmentResultRepo.save(obj);
		
		audit = new Audit(updateBy, "AssessmentResult", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteAssessmentResult(UUID id, String updateBy) {

		Optional<AssessmentResult> oe = this.assessmentResultRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		AssessmentResult obj = oe.get();
		
		audit = new Audit(updateBy, "AssessmentResult", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		assessmentResultRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateAssessmentResult(AssessmentResult assessmentResult, String updateBy) {
		
		Optional<AssessmentResult> oe = this.assessmentResultRepo.findById(assessmentResult.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		AssessmentResult obj = oe.get();
		
		obj.setAssessment(assessmentResult.getAssessment());
		obj.setStudent(assessmentResult.getStudent());
		obj.setSubject(assessmentResult.getSubject());
		obj.setMaxMarks(assessmentResult.getMaxMarks());
		obj.setMarksObtained(assessmentResult.getMarksObtained());
		
		obj = assessmentResultRepo.save(obj);
		
		audit = new Audit(updateBy, "AssessmentResult", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

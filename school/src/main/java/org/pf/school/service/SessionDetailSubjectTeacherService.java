package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.SessionDetailSubjectTeacher;
import org.pf.school.model.Audit;
import org.pf.school.repository.SessionDetailSubjectTeacherRepo;
import org.pf.school.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SessionDetailSubjectTeacherService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private SessionDetailSubjectTeacherRepo sessionDetailSubjectTeacherRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<SessionDetailSubjectTeacher> oe = this.sessionDetailSubjectTeacherRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addSessionDetailSubjectTeacher(SessionDetailSubjectTeacher obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = sessionDetailSubjectTeacherRepo.save(obj);
		
		audit = new Audit(updateBy, "SessionDetailSubjectTeacher", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSessionDetailSubjectTeacher(UUID id, String updateBy) {

		Optional<SessionDetailSubjectTeacher> oe = this.sessionDetailSubjectTeacherRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		SessionDetailSubjectTeacher obj = oe.get();
		
		audit = new Audit(updateBy, "SessionDetailSubjectTeacher", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		sessionDetailSubjectTeacherRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSessionDetailSubjectTeacher(SessionDetailSubjectTeacher sessionDetailSubjectTeacher, String updateBy) {
		
		Optional<SessionDetailSubjectTeacher> oe = this.sessionDetailSubjectTeacherRepo.findById(sessionDetailSubjectTeacher.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		SessionDetailSubjectTeacher obj = oe.get();
		
		obj.setSessionDetail(sessionDetailSubjectTeacher.getSessionDetail());
		obj.setSubject(sessionDetailSubjectTeacher.getSubject());
		obj.setTeacher(sessionDetailSubjectTeacher.getTeacher());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = sessionDetailSubjectTeacherRepo.save(obj);
		
		audit = new Audit(updateBy, "SessionDetailSubjectTeacher", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

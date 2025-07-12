package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.SessionDetail;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.SessionDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SessionDetailService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private SessionDetailRepo sessionDetailRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<SessionDetail> oe = this.sessionDetailRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addSessionDetail(SessionDetail obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = sessionDetailRepo.save(obj);
		
		audit = new Audit(updateBy, "SessionDetail", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSessionDetail(UUID id, String updateBy) {

		Optional<SessionDetail> oe = this.sessionDetailRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		SessionDetail obj = oe.get();
		
		audit = new Audit(updateBy, "SessionDetail", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		sessionDetailRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSessionDetail(SessionDetail sessionDetail, String updateBy) {
		
		Optional<SessionDetail> oe = this.sessionDetailRepo.findById(sessionDetail.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		SessionDetail obj = oe.get();
		
		obj.setSession(sessionDetail.getSession());
		obj.setSchoolClass(sessionDetail.getSchoolClass());
		obj.setClassTeacher(sessionDetail.getClassTeacher());
		obj.setFee(sessionDetail.getFee());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = sessionDetailRepo.save(obj);
		
		audit = new Audit(updateBy, "SessionDetail", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}


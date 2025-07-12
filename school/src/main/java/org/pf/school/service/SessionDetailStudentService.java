package org.pf.school.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.SessionDetailStudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SessionDetailStudentService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private SessionDetailStudentRepo sessionDetailStudentRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<SessionDetailStudent> oe = this.sessionDetailStudentRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional 
	public List<UUID> getListStudentRegistered() {
		List<UUID> listStudentRegistered = new ArrayList<UUID>();
		
		for(SessionDetailStudent obj : this.sessionDetailStudentRepo.findAll()) {
			listStudentRegistered.add(obj.getStudent().getId());
		}
		
		return listStudentRegistered;
	}
	
	@Transactional
	public TransactionResult addSessionDetailStudent(SessionDetailStudent obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = sessionDetailStudentRepo.save(obj);
		
		audit = new Audit(updateBy, "SessionDetailStudent", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSessionDetailStudent(UUID id, String updateBy) {

		Optional<SessionDetailStudent> oe = this.sessionDetailStudentRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		SessionDetailStudent obj = oe.get();
		
		audit = new Audit(updateBy, "SessionDetailStudent", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		sessionDetailStudentRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSessionDetailStudent(SessionDetailStudent sessionDetailStudent, String updateBy) {
		
		Optional<SessionDetailStudent> oe = this.sessionDetailStudentRepo.findById(sessionDetailStudent.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		SessionDetailStudent obj = oe.get();
		
		obj.setSessionDetail(sessionDetailStudent.getSessionDetail());
		obj.setStudent(sessionDetailStudent.getStudent());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = sessionDetailStudentRepo.save(obj);
		
		audit = new Audit(updateBy, "SessionDetailStudent", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}



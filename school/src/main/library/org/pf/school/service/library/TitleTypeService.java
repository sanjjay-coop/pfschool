package org.pf.school.service.library;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.library.TitleType;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.library.TitleTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TitleTypeService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private TitleTypeRepo titleTypeRepo;
	
	private Audit audit;
	
	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<TitleType> oe = this.titleTypeRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addTitleType(TitleType obj, String updateBy) {
		
		obj = titleTypeRepo.save(obj);
		
		audit = new Audit(updateBy, "TitleType", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);

		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteTitleType(UUID id, String updateBy) {

		Optional<TitleType> oe = this.titleTypeRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		TitleType obj = oe.get();
		
		audit = new Audit(updateBy, "TitleType", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		titleTypeRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateTitleType(TitleType titleType, String updateBy) {
		
		Optional<TitleType> oe = this.titleTypeRepo.findById(titleType.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		TitleType obj = oe.get();
		
		obj.setName(titleType.getName());
		
		obj = titleTypeRepo.save(obj);
		
		audit = new Audit(updateBy, "TitleType", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

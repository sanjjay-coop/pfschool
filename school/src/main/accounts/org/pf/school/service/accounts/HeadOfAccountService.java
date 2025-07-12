package org.pf.school.service.accounts;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.accounts.HeadOfAccount;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.accounts.HeadOfAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class HeadOfAccountService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private HeadOfAccountRepo headOfAccountRepo;
	
	private Audit audit;
	
	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<HeadOfAccount> oe = this.headOfAccountRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addHeadOfAccount(HeadOfAccount obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = headOfAccountRepo.save(obj);
		
		audit = new Audit(updateBy, "HeadOfAccount", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);

		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteHeadOfAccount(UUID id, String updateBy) {

		Optional<HeadOfAccount> oe = this.headOfAccountRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		HeadOfAccount obj = oe.get();
		
		audit = new Audit(updateBy, "HeadOfAccount", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		headOfAccountRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateHeadOfAccount(HeadOfAccount headOfAccount, String updateBy) {
		
		Optional<HeadOfAccount> oe = this.headOfAccountRepo.findById(headOfAccount.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		HeadOfAccount obj = oe.get();
		
		obj.setCode(headOfAccount.getCode());
		obj.setName(headOfAccount.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = headOfAccountRepo.save(obj);
		
		audit = new Audit(updateBy, "HeadOfAccount", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

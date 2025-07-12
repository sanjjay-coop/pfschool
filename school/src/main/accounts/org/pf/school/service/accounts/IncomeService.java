package org.pf.school.service.accounts;

import java.util.Calendar;
import java.util.Optional;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.accounts.Income;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.accounts.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class IncomeService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private IncomeRepo incomeRepo;
	
	private Audit audit;
	
	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Income> oe = this.incomeRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addIncome(Income obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = incomeRepo.save(obj);
		
		audit = new Audit(updateBy, "Income", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);

		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteIncome(Long id, String updateBy) {

		Optional<Income> oe = this.incomeRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Income obj = oe.get();
		
		audit = new Audit(updateBy, "Income", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		incomeRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateIncome(Income income, String updateBy) {
		
		Optional<Income> oe = this.incomeRepo.findById(income.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Income obj = oe.get();
		
		obj.setAmount(income.getAmount());
		obj.setHeadOfAccount(income.getHeadOfAccount());
		obj.setModeOfReceipt(income.getModeOfReceipt());
		obj.setNarration(income.getNarration());
		obj.setReceiptDate(income.getReceiptDate());
		obj.setReceiptNumber(income.getReceiptNumber());
		obj.setReceivedFrom(income.getReceivedFrom());
		obj.setTransactionDate(income.getTransactionDate());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = incomeRepo.save(obj);
		
		audit = new Audit(updateBy, "Income", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

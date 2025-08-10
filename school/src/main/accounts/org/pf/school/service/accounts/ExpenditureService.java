package org.pf.school.service.accounts;

import java.util.Calendar;
import java.util.Optional;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.accounts.Expenditure;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.accounts.ExpenditureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ExpenditureService {

	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private ExpenditureRepo expenditureRepo;
	
	private Audit audit;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Expenditure> oe = this.expenditureRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addExpenditure(Expenditure obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = expenditureRepo.save(obj);
		
		audit = new Audit(updateBy, "Expenditure", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);

		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteExpenditure(Long id, String updateBy) {

		Optional<Expenditure> oe = this.expenditureRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Expenditure obj = oe.get();
		
		audit = new Audit(updateBy, "Expenditure", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		expenditureRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateExpenditure(Expenditure expenditure, String updateBy) {
		
		Optional<Expenditure> oe = this.expenditureRepo.findById(expenditure.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Expenditure obj = oe.get();
		
		obj.setAmount(expenditure.getAmount());
		obj.setHeadOfAccount(expenditure.getHeadOfAccount());
		obj.setModeOfPayment(expenditure.getModeOfPayment());
		obj.setNarration(expenditure.getNarration());
		obj.setVoucherDate(expenditure.getVoucherDate());
		obj.setVoucherInvoiceNumber(expenditure.getVoucherInvoiceNumber());
		obj.setPaidTo(expenditure.getPaidTo());
		obj.setTransactionDate(expenditure.getTransactionDate());
		obj.setTowards(expenditure.getTowards());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = expenditureRepo.save(obj);
		
		audit = new Audit(updateBy, "Expenditure", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

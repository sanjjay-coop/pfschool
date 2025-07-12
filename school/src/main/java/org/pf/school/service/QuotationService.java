package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Quotation;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.QuotationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class QuotationService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private QuotationRepo quotationRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Quotation> oe = this.quotationRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addQuotation(Quotation obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		obj = quotationRepo.save(obj);
	
		audit = new Audit(updateBy, "Quotation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteQuotation(UUID id, String updateBy) {

		Optional<Quotation> oe = this.quotationRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Quotation obj = oe.get();
		
		audit = new Audit(updateBy, "Quotation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		quotationRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateQuotation(Quotation quotation, String updateBy) {
		
		Optional<Quotation> oe = this.quotationRepo.findById(quotation.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Quotation obj = oe.get();
				
		obj.setQuote(quotation.getQuote());
		obj.setAuthor(quotation.getAuthor());
		obj.setSource(quotation.getSource());
		
		obj.setUpdateDefaults(updateBy);
		obj = quotationRepo.save(obj);
		
		audit = new Audit(updateBy, "Quotation", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

}




package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Category;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Category> oe = this.categoryRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addCategory(Category obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = categoryRepo.save(obj);
		
		audit = new Audit(updateBy, "Category", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteCategory(UUID id, String updateBy) {

		Optional<Category> oe = this.categoryRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Category obj = oe.get();
		
		audit = new Audit(updateBy, "Category", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		categoryRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateCategory(Category category, String updateBy) {
		
		Optional<Category> oe = this.categoryRepo.findById(category.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Category obj = oe.get();
		
		obj.setCode(category.getCode());
		obj.setName(category.getName());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = categoryRepo.save(obj);
		
		audit = new Audit(updateBy, "Category", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

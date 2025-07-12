package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Role;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class RoleService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private RoleRepo roleRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Role> oe = this.roleRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addRole(Role obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = roleRepo.save(obj);
		
		audit = new Audit(updateBy, "Role", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteRole(UUID id, String updateBy) {

		Optional<Role> oe = this.roleRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Role obj = oe.get();
		
		audit = new Audit(updateBy, "Role", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		roleRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateRole(Role role, String updateBy) {
		
		Optional<Role> oe = this.roleRepo.findById(role.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Role obj = oe.get();
		
		obj.setCode(role.getCode());
		obj.setDescription(role.getDescription());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = roleRepo.save(obj);
		
		audit = new Audit(updateBy, "Role", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

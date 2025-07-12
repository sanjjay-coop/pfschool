package org.pf.school.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Parameters;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.ParametersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ParametersService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private ParametersRepo parametersRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Parameters> oe = this.parametersRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	public Parameters getParameters() {
		List<Parameters> listParameters = parametersRepo.findAll(Sort.by(Sort.Direction.DESC, "recordAddDate"));
		
		if (listParameters.isEmpty()) {
			return null;
		} else {
			return listParameters.get(0);
		}
	}
	
	@Transactional
	public TransactionResult addParameters(Parameters obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		obj = parametersRepo.save(obj);
		
		audit = new Audit(updateBy, "Parameters", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}
}

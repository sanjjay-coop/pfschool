package org.pf.school.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Leader;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.LeaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class LeaderService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private LeaderRepo leaderRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Leader> oe = this.leaderRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addLeader(Leader obj, String updateBy) throws IOException {
		
		if (!obj.getFile().isEmpty()) {
			String fileName = StringUtils.cleanPath(obj.getFile().getOriginalFilename());
			obj.setFileName(fileName);
			obj.setFileType(obj.getFile().getContentType());
			obj.setFileData(obj.getFile().getBytes());
		}
		
		obj.setAddDefaults(updateBy);
		
		obj = leaderRepo.save(obj);
		
		audit = new Audit(updateBy, "Leader", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteLeader(UUID id, String updateBy) {

		Optional<Leader> oe = this.leaderRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Leader obj = oe.get();
		
		audit = new Audit(updateBy, "Leader", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		leaderRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateLeader(Leader leader, String updateBy) throws IOException {
		
		Optional<Leader> oe = this.leaderRepo.findById(leader.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Leader obj = oe.get();
		
		if (!leader.getFile().isEmpty()) {
			String fileName = StringUtils.cleanPath(leader.getFile().getOriginalFilename());
			obj.setFileName(fileName);
			obj.setFileType(leader.getFile().getContentType());
			obj.setFileData(leader.getFile().getBytes());
		}
		
		obj.setName(leader.getName());
		obj.setAbout(leader.getAbout());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = leaderRepo.save(obj);
		
		audit = new Audit(updateBy, "Leader", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

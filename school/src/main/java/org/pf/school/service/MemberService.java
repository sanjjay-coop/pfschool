package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.forms.ChangePasswordForm;
import org.pf.school.model.Audit;
import org.pf.school.model.Member;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MemberService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private MemberRepo memberRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Member> oe = this.memberRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addMember(Member obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = memberRepo.save(obj);
		
		audit = new Audit(updateBy, "Member", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteMember(UUID id, String updateBy) {

		Optional<Member> oe = this.memberRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Member obj = oe.get();
		
		audit = new Audit(updateBy, "Member", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		memberRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateMember(Member member, String updateBy) {
		
		Optional<Member> oe = this.memberRepo.findById(member.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Member obj = oe.get();
		
		obj.setAccountNonExpired(member.getAccountNonExpired());
		obj.setAccountNonLocked(member.getAccountNonLocked());
		obj.setCredentialsNonExpired(member.getCredentialsNonExpired());
		obj.setEnabled(member.getEnabled());
		obj.setRoles(member.getRoles());
	
		obj.setUpdateDefaults(updateBy);
		
		obj = memberRepo.save(obj);
		
		audit = new Audit(updateBy, "Member", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult changePassword(ChangePasswordForm cpf, String updateBy) {
		
		Member member = this.memberRepo.findByUidIgnoreCase(updateBy);
		
		if (member == null) return new TransactionResult(member, false, "Record not found. Password not changed.");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if (!passwordEncoder.matches(cpf.getCurrentPassword(), member.getPassword())) {
			return new TransactionResult(member, false, "Current password did not match. Password not changed.");
		}
		
		member.setPassword((new BCryptPasswordEncoder()).encode(cpf.getNewPassword()));
		
		member = memberRepo.save(member);
	
		audit = new Audit(updateBy, "Member", member.toString(), member.getId(), Calendar.getInstance().getTime(), "CHGPASSWD");
		auditRepo.save(audit);
		
		return new TransactionResult(member, true);
	}
}

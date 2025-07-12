package org.pf.school.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;
import java.util.TreeSet;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Member;
import org.pf.school.model.Role;
import org.pf.school.model.Staff;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.RoleRepo;
import org.pf.school.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class StaffService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private StaffRepo staffRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private RoleRepo roleRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Staff> oe = this.staffRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addStaff(Staff obj, String updateBy) throws IOException {
		
		Role role = this.roleRepo.findByCodeIgnoreCase(obj.getRole());
		
		if (role == null) {
			role = new Role();
			role.setCode(obj.getRole());
			role.setDescription(obj.getRole());
			role.setAddDefaults(updateBy);
			
			this.roleRepo.save(role);
			
			audit = new Audit(updateBy, "Role", role.toString(), role.getId(), Calendar.getInstance().getTime(), "ADD");
			auditRepo.save(audit);
		}
		obj.setAddDefaults(updateBy);
		
		TreeSet<Role> roleSet = new TreeSet<Role>();
		
		roleSet.add(role);
		
		Member member = new Member();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		member.setUid(obj.getStaffId());
		member.setPassword(passwordEncoder.encode(obj.getMobile()));
		member.setName(obj.getName());
		member.setAccountNonExpired(true);
		member.setAccountNonLocked(true);
		member.setCredentialsNonExpired(true);
		member.setEnabled(true);
		member.setRoles(roleSet);
		
		member.setAddDefaults(updateBy);
		
		memberRepo.save(member);
		
		audit = new Audit(updateBy, "Member", member.toString(), member.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		obj.setMember(member);
		
		if (!obj.getFile().isEmpty()) {
			String fileName = StringUtils.cleanPath(obj.getFile().getOriginalFilename());
			obj.setFileName(fileName);
			obj.setFileType(obj.getFile().getContentType());
			obj.setFileData(obj.getFile().getBytes());
		}
		
		obj = staffRepo.save(obj);
		
		audit = new Audit(updateBy, "Staff", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteStaff(UUID id, String updateBy) {

		Optional<Staff> oe = this.staffRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Staff obj = oe.get();
		
		audit = new Audit(updateBy, "Staff", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		staffRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateStaff(Staff staff, String updateBy) throws IOException{
		
		Optional<Staff> oe = this.staffRepo.findById(staff.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Staff obj = oe.get();
		
		obj.setStaffId(staff.getStaffId());
		obj.setName(staff.getName());
		obj.setSpouseName(staff.getSpouseName());
		obj.setDateOfBirth(staff.getDateOfBirth());
		obj.setResidenceAddress(staff.getResidenceAddress());
		obj.setPermanentAddress(staff.getPermanentAddress());
		obj.setAadhaar(staff.getAadhaar());
		obj.setPan(staff.getPan());
		obj.setPhone(staff.getPhone());
		obj.setMobile(staff.getMobile());
		obj.setEmail(staff.getEmail());
		obj.setRemarks(staff.getRemarks());
		obj.setCategory(staff.getCategory());
		obj.setGender(staff.getGender());
		obj.setDesignation(staff.getDesignation());
		
		obj.setUpdateDefaults(updateBy);
		
		obj.getMember().setUid(staff.getStaffId());
		obj.getMember().setName(staff.getName());
		obj.getMember().setUpdateDefaults(updateBy);
		
		if (!staff.getFile().isEmpty()) {
			String fileName = StringUtils.cleanPath(staff.getFile().getOriginalFilename());
			obj.setFileName(fileName);
			obj.setFileType(staff.getFile().getContentType());
			obj.setFileData(staff.getFile().getBytes());
		}
		
		obj = staffRepo.save(obj);
		
		audit = new Audit(updateBy, "Staff", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

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
import org.pf.school.model.Student;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.RoleRepo;
import org.pf.school.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private MemberRepo memberRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Student> oe = this.studentRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addStudent(Student obj, String updateBy) throws IOException {
		
		Role role = this.roleRepo.findByCodeIgnoreCase("ROLE_STUDENT");
		
		if (role == null) {
			role = new Role();
			role.setCode("ROLE_STUDENT");
			role.setDescription("ROLE STUDENT");
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
		
		member.setUid(obj.getStudentId());
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
		
		obj = studentRepo.save(obj);
		
		audit = new Audit(updateBy, "Student", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteStudent(UUID id, String updateBy) {

		Optional<Student> oe = this.studentRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Student obj = oe.get();
		
		audit = new Audit(updateBy, "Student", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		studentRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateStudent(Student student, String updateBy) throws IOException {
		
		Optional<Student> oe = this.studentRepo.findById(student.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Student obj = oe.get();
		
		obj.setStudentId(student.getStudentId());
		obj.setName(student.getName());
		obj.setDateOfAdmission(student.getDateOfAdmission());
		obj.setDateOfBirth(student.getDateOfBirth());
		obj.setPlaceOfBirth(student.getPlaceOfBirth());
		obj.setCategory(student.getCategory());
		obj.setGender(student.getGender());
		obj.setNameOfFather(student.getNameOfFather());
		obj.setNameOfMother(student.getNameOfMother());
		obj.setOfficeAddressOfParent(student.getOfficeAddressOfParent());
		obj.setResidenceAddress(student.getResidenceAddress());
		obj.setBloodGroup(student.getBloodGroup());
		obj.setAadhaar(student.getAadhaar());
		obj.setPhoneOffice(student.getPhoneOffice());
		obj.setPhoneResidence(student.getPhoneResidence());
		obj.setMobile(student.getMobile());
		obj.setEmail(student.getEmail());
		obj.setEmergencyContact(student.getEmergencyContact());
		obj.setMotherTongue(student.getMotherTongue());
		obj.setIdentificationMarkOne(student.getIdentificationMarkOne());
		obj.setIdentificationMarkTwo(student.getIdentificationMarkTwo());
		obj.setRemarks(student.getRemarks());
		obj.setAdmissionClass(student.getAdmissionClass());
		
		obj.setUpdateDefaults(updateBy);
		
		obj.getMember().setUid(student.getStudentId());
		obj.getMember().setName(student.getName());
		obj.getMember().setUpdateDefaults(updateBy);
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		obj.getMember().setPassword(passwordEncoder.encode(student.getMobile()));
		
		if (!student.getFile().isEmpty()) {
			String fileName = StringUtils.cleanPath(student.getFile().getOriginalFilename());
			obj.setFileName(fileName);
			obj.setFileType(student.getFile().getContentType());
			obj.setFileData(student.getFile().getBytes());
		}
		
		obj = studentRepo.save(obj);
		
		audit = new Audit(updateBy, "Student", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}


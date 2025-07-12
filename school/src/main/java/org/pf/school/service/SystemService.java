package org.pf.school.service;

import java.util.Calendar;
import java.util.TreeSet;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Category;
import org.pf.school.model.Gender;
import org.pf.school.model.Member;
import org.pf.school.model.Role;
import org.pf.school.model.Staff;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.CategoryRepo;
import org.pf.school.repository.GenderRepo;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.RoleRepo;
import org.pf.school.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class SystemService {
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private AuditRepo auditRepo;
	
	@Autowired
	private StaffRepo staffRepo;
	
	@Autowired
	private GenderRepo genderRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
		
	@Transactional
	public TransactionResult initializeDatabase(Staff staff) {
		
		System.out.println("Initializing database ...");
		
		try {
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			staff.setAddDefaults("system");
			
			System.out.println("Initializing database ...: Adding Role");
			
			Role role = new Role();
			
			role.setCode("ROLE_MANAGER");
			role.setDescription("Manager Role");
			
			role.setAddDefaults("system");
			
			roleRepo.save(role);
			
			auditRepo.save(new Audit("system", "Role", role.toString(), role.getId(), Calendar.getInstance().getTime(), "ADD"));
			
			System.out.println("Initializing database ...: Adding Member");
			
			Member member = new Member();
			
			member.setAccountNonExpired(true);
			member.setAccountNonLocked(true);
			member.setCredentialsNonExpired(true);
			member.setEnabled(true);
			member.setPassword(passwordEncoder.encode(staff.getPassword()));
			member.setRoles(new TreeSet<Role>());
			member.getRoles().add(role);
			member.setUid(staff.getStaffId());
			member.setAddDefaults("system");
			
			memberRepo.save(member);
			
			auditRepo.save(new Audit("system", "Member", member.toString(), member.getId(), Calendar.getInstance().getTime(), "ADD"));
			
			System.out.println("Initializing database ...: Adding Gender Record");
			
			Gender gender = new Gender();
			
			gender.setCode("M");
			gender.setName("Male");
			gender.setAddDefaults("system");
			
			genderRepo.save(gender);
			
			auditRepo.save(new Audit("system", "Gender", gender.toString(), gender.getId(), Calendar.getInstance().getTime(), "ADD"));
			
			System.out.println("Initializing database ...: Adding Category Record");
			
			Category category = new Category();
			
			category.setCode("GEN");
			category.setName("General");
			category.setAddDefaults("system");
			
			categoryRepo.save(category);
			
			auditRepo.save(new Audit("system", "Category", category.toString(), category.getId(), Calendar.getInstance().getTime(), "ADD"));
			
			
			System.out.println("Initializing database ...: Adding Staff Record");
			
			staff.setGender(gender);
			staff.setCategory(category);
			
			staff.setMember(member);
			
			staffRepo.save(staff);
			
			auditRepo.save(new Audit("system", "Staff", staff.toString(), staff.getId(), Calendar.getInstance().getTime(), "ADD"));
			
			return new TransactionResult(true, "Database initialised successfully.");
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			
			return new TransactionResult(false, "Database initialization failed.");
		}
	}
}

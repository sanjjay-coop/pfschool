package org.pf.school.controller.home.teacher;

import java.security.Principal;
import java.util.List;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.Member;
import org.pf.school.model.Staff;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.RoleRepo;
import org.pf.school.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/home/teacher/list")
public class TeacherListController extends HomeBaseController {

	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@GetMapping
	public String teacherView(Model model, RedirectAttributes reat, Principal principal) {
		
		List<Member> listMember = this.memberRepo.findByRoles(this.roleRepo.findByCodeIgnoreCase("ROLE_STAFF_TEACHER"));		
		
		List<Staff> listTeacher = this.staffRepo.findByMemberInOrderByNameAsc(listMember);
		
		model.addAttribute("listTeacher", listTeacher);
		
		return "home/teacher/list";
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "teacher";
	}
	
}

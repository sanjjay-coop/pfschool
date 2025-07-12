package org.pf.school.controller.dashboard.profile;

import java.security.Principal;

import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.model.Staff;
import org.pf.school.model.Student;
import org.pf.school.repository.StaffRepo;
import org.pf.school.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/profile")
public class SchoolProfileController extends DashboardBaseController {

	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@GetMapping
	public String profileView(Model model, RedirectAttributes reat, Principal principal) {
		
		Student student = this.studentRepo.findByMember_Uid(principal.getName());
		
		if (student != null) {
			
			model.addAttribute("student", student);
			
			return "dashboard/profile/student";
		} else {
			
			Staff staff = this.staffRepo.findByMember_Uid(principal.getName());
			
			if (staff != null) {
				model.addAttribute("staff", staff);
				
				return "dashboard/profile/staff";
			} else {
				reat.addFlashAttribute("message", "Something went wrong! Contact system administrator.");
				return "redirect:/";
			}
		}
	}
}

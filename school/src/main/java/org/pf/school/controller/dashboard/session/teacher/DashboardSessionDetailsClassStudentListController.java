package org.pf.school.controller.dashboard.session.teacher;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.model.Staff;
import org.pf.school.repository.SessionDetailRepo;
import org.pf.school.repository.SessionDetailStudentRepo;
import org.pf.school.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/class/student/list")
public class DashboardSessionDetailsClassStudentListController extends DashboardBaseController {

	@Autowired
	SessionDetailRepo sessionDetailRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@Autowired
	SessionDetailStudentRepo sessionDetailStudentRepo;
	
	@GetMapping("/{id}")
	public String sessionDetailStudentView(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		Staff staff = this.staffRepo.findByMember_Uid(principal.getName());
		
		if (staff==null) {
			reat.addFlashAttribute("message", "No such staff record found.");
			return "redirect:/dashboard";
		}
		
		SessionDetail sessionDetail = this.sessionDetailRepo.findByIdAndClassTeacher(id, staff);
		
		if (sessionDetail == null) {
			reat.addFlashAttribute("message", "No such record is found.");
			return "redirect:/dashboard";
		}
		
		List<SessionDetailStudent> listSessionDetailStudent = this.sessionDetailStudentRepo.findBySessionDetailOrderByStudent_nameAsc(sessionDetail);
		
		Calendar cal = Calendar.getInstance();
		
		model.addAttribute("currentMonth", cal.get(Calendar.MONTH));
		model.addAttribute("currentYear", cal.get(Calendar.YEAR));
		model.addAttribute("sessionDetail", sessionDetail);
		model.addAttribute("listSessionDetailStudent", listSessionDetailStudent);
		
		return "dashboard/session/details/teacher/class/students";
	}
}

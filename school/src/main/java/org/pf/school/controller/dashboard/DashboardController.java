package org.pf.school.controller.dashboard;

import java.security.Principal;
import java.util.List;

import org.pf.school.model.SchoolMessage;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.SchoolMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController extends DashboardBaseController {

	@Autowired
	private SchoolMessageRepo messageRepo;
	
	@Autowired
	private MemberRepo memberRepo;
	
	@GetMapping("/dashboard")
	public String dashboardView(Model model, RedirectAttributes reat, Principal principal) {
		
		List<SchoolMessage> listSchoolMessage = this.messageRepo.findByMessageToOrderByRecordAddDateDesc(this.memberRepo.findByUidIgnoreCase(principal.getName()));
		
		model.addAttribute("listSchoolMessage", listSchoolMessage);
		
		return "dashboard/default";
	}
}

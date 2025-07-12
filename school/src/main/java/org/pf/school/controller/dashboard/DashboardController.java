package org.pf.school.controller.dashboard;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController extends DashboardBaseController {

	@GetMapping("/dashboard")
	public String dashboardView(Model model, RedirectAttributes reat, Principal principal) {
		
		return "dashboard/default";
	}
}

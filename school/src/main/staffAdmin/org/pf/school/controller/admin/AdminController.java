package org.pf.school.controller.admin;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController extends AdminBaseController {
	
	@GetMapping("/admin")
	public String adminView(Model model, Principal principal) {
		
		return "admin/default";
		
	}
}
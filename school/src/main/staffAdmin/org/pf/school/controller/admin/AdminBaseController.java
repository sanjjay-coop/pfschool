package org.pf.school.controller.admin;

import org.pf.school.controller.BaseController;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AdminBaseController extends BaseController {
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "admin";
	}
	
}

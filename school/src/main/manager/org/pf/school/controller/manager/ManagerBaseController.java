package org.pf.school.controller.manager;

import org.pf.school.controller.BaseController;
import org.springframework.web.bind.annotation.ModelAttribute;

public class ManagerBaseController extends BaseController {

	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "manager";
	}
	
}

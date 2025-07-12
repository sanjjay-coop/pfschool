package org.pf.school.controller.dashboard;

import org.pf.school.controller.BaseController;
import org.springframework.web.bind.annotation.ModelAttribute;

public class DashboardBaseController extends BaseController {
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "dashboard";
	}
}

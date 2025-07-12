package org.pf.school.controller.accounts;

import org.pf.school.controller.BaseController;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AccountsBaseController extends BaseController {
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "accounts";
	}
	
}

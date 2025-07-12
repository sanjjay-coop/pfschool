package org.pf.school.controller.library;

import org.pf.school.controller.BaseController;
import org.springframework.web.bind.annotation.ModelAttribute;

public class LibraryBaseController extends BaseController {
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "library";
	}
	
}

package org.pf.school.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DisclaimerController extends HomeBaseController {

	@GetMapping("/home/disclaimer")
	public String disclaimerView(Model model) {
		
		model.addAttribute("viewLeftMenu", "disclaimer");
		
		return "home/disclaimer";
	}
	
	@GetMapping("/home/privacyPolicy")
	public String privacyPolicyView(Model model) {

		model.addAttribute("viewLeftMenu", "privacy");
		
		return "home/privacyPolicy";
	}
	
	@GetMapping("/home/termsAndConditions")
	public String termsAndConditionsView(Model model) {

		model.addAttribute("viewLeftMenu", "tandc");
		
		return "home/termsAndConditions";
	}
}

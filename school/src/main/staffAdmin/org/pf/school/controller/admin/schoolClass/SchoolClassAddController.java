package org.pf.school.controller.admin.schoolClass;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.SchoolClass;
import org.pf.school.service.SchoolClassService;
import org.pf.school.validator.add.SchoolClassAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/schoolClass/addNew")
public class SchoolClassAddController extends AdminBaseController {

	@Autowired
	private SchoolClassService schoolClassService;
	
	@Autowired
	private SchoolClassAddValidator schoolClassAddValidator;
	
	@GetMapping
	public String schoolClassAdd(Model model) {
		
		SchoolClass schoolClass = new SchoolClass();
		
		model.addAttribute(schoolClass);
		
		return "admin/schoolClass/addNew";
	}
	
	@PostMapping
	public String schoolClassAdd(@ModelAttribute SchoolClass schoolClass,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.schoolClassAddValidator.validate(schoolClass, result);
		
		if (result.hasErrors()) {
			return "admin/schoolClass/addNew";
		}
		
		try {
			TransactionResult tr = this.schoolClassService.addSchoolClass(schoolClass, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/schoolClass/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/schoolClass/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/schoolClass/addNew";
		}
	}
}

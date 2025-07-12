package org.pf.school.controller.admin.academicSession;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.AcademicSession;
import org.pf.school.service.AcademicSessionService;
import org.pf.school.validator.add.AcademicSessionAddValidator;
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
@RequestMapping(value = "/admin/academicSession/addNew")
public class AcademicSessionAddController extends AdminBaseController {

	@Autowired
	private AcademicSessionService academicSessionService;
	
	@Autowired
	private AcademicSessionAddValidator academicSessionAddValidator;
	
	@GetMapping
	public String academicSessionAdd(Model model) {
		
		AcademicSession academicSession = new AcademicSession();
		
		model.addAttribute(academicSession);
		
		return "admin/academicSession/addNew";
	}
	
	@PostMapping
	public String academicSessionAdd(@ModelAttribute AcademicSession academicSession,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.academicSessionAddValidator.validate(academicSession, result);
		
		if (result.hasErrors()) {
			return "admin/academicSession/addNew";
		}
		
		try {
			TransactionResult tr = this.academicSessionService.addAcademicSession(academicSession, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/academicSession/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/academicSession/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/academicSession/addNew";
		}
	}
}

package org.pf.school.controller.admin.subject;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Subject;
import org.pf.school.service.SubjectService;
import org.pf.school.validator.add.SubjectAddValidator;
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
@RequestMapping(value = "/admin/subject/addNew")
public class SubjectAddController extends AdminBaseController {

	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private SubjectAddValidator subjectAddValidator;
	
	@GetMapping
	public String subjectAdd(Model model) {
		
		Subject subject = new Subject();
		
		model.addAttribute(subject);
		
		return "admin/subject/addNew";
	}
	
	@PostMapping
	public String subjectAdd(@ModelAttribute Subject subject,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.subjectAddValidator.validate(subject, result);
		
		if (result.hasErrors()) {
			return "admin/subject/addNew";
		}
		
		try {
			TransactionResult tr = this.subjectService.addSubject(subject, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/subject/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/subject/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/subject/addNew";
		}
	}
}


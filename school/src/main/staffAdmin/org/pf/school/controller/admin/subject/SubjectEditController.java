package org.pf.school.controller.admin.subject;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Subject;
import org.pf.school.service.SubjectService;
import org.pf.school.validator.edit.SubjectEditValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/subject/edit")
public class SubjectEditController extends AdminBaseController {

	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private SubjectEditValidator subjectEditValidator;
	
	@GetMapping("/{id}")
	public String editSubject(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Subject subject = (Subject) this.subjectService.getById(id);
			
			if (subject == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/subject/addNew";
			}
	
			model.addAttribute("subject", subject);
			
			return "admin/subject/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/subject/addNew";
		}
	}

	@PostMapping("/*")
	public String editSubject(@ModelAttribute Subject subject,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.subjectEditValidator.validate(subject, result);
		
		if (result.hasErrors()) {
			return "admin/subject/edit";
		}
		
		try {
			TransactionResult tr = this.subjectService.updateSubject(subject, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/subject/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/subject/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/subject/edit/"+subject.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/subject/edit/"+subject.getId();
		}
	}
}

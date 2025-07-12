package org.pf.school.controller.admin.academicSession;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.AcademicSession;
import org.pf.school.service.AcademicSessionService;
import org.pf.school.validator.edit.AcademicSessionEditValidator;
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
@RequestMapping("/admin/academicSession/edit")
public class AcademicSessionEditController extends AdminBaseController {

	@Autowired
	private AcademicSessionService academicSessionService;
	
	@Autowired
	private AcademicSessionEditValidator academicSessionEditValidator;
	
	@GetMapping("/{id}")
	public String editAcademicSession(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			AcademicSession academicSession = (AcademicSession) this.academicSessionService.getById(id);
			
			if (academicSession == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/academicSession/addNew";
			}
	
			model.addAttribute("academicSession", academicSession);
			
			return "admin/academicSession/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/academicSession/addNew";
		}
	}

	@PostMapping("/*")
	public String editAcademicSession(@ModelAttribute AcademicSession academicSession,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.academicSessionEditValidator.validate(academicSession, result);
		
		if (result.hasErrors()) {
			return "admin/academicSession/edit";
		}
		
		try {
			TransactionResult tr = this.academicSessionService.updateAcademicSession(academicSession, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/academicSession/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/academicSession/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/academicSession/edit/"+academicSession.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/academicSession/edit/"+academicSession.getId();
		}
	}
}
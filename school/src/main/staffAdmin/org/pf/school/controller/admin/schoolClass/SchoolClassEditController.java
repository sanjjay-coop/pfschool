package org.pf.school.controller.admin.schoolClass;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.SchoolClass;
import org.pf.school.service.SchoolClassService;
import org.pf.school.validator.edit.SchoolClassEditValidator;
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
@RequestMapping("/admin/schoolClass/edit")
public class SchoolClassEditController extends AdminBaseController {

	@Autowired
	private SchoolClassService schoolClassService;
	
	@Autowired
	private SchoolClassEditValidator schoolClassEditValidator;
	
	@GetMapping("/{id}")
	public String editSchoolClass(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			SchoolClass schoolClass = (SchoolClass) this.schoolClassService.getById(id);
			
			if (schoolClass == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/schoolClass/addNew";
			}
	
			model.addAttribute("schoolClass", schoolClass);
			
			return "admin/schoolClass/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/schoolClass/addNew";
		}
	}

	@PostMapping("/*")
	public String editSchoolClass(@ModelAttribute SchoolClass schoolClass,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.schoolClassEditValidator.validate(schoolClass, result);
		
		if (result.hasErrors()) {
			return "admin/schoolClass/edit";
		}
		
		try {
			TransactionResult tr = this.schoolClassService.updateSchoolClass(schoolClass, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/schoolClass/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/schoolClass/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/schoolClass/edit/"+schoolClass.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/schoolClass/edit/"+schoolClass.getId();
		}
	}
}


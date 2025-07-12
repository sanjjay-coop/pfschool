package org.pf.school.controller.admin.academicCalendar;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.AcademicCalendar;
import org.pf.school.model.AcademicSession;
import org.pf.school.repository.AcademicSessionRepo;
import org.pf.school.service.AcademicCalendarService;
import org.pf.school.validator.AcademicCalendarValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/admin/academicCalendar/edit")
public class AcademicCalendarEditController extends AdminBaseController {

	@Autowired
	private AcademicCalendarService academicCalendarService;
	
	@Autowired
	private AcademicCalendarValidator academicCalendarValidator;
	
	@Autowired
	private AcademicSessionRepo academicSessionRepo;
	
	@ModelAttribute("listAcademicSession")
	public List<AcademicSession> getListAcademicSession() {
		return this.academicSessionRepo.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
	}
	
	@GetMapping("/{id}")
	public String editAcademicCalendar(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			AcademicCalendar academicCalendar = (AcademicCalendar) this.academicCalendarService.getById(id);
			
			if (academicCalendar == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/academicCalendar/addNew";
			}
	
			model.addAttribute("academicCalendar", academicCalendar);
			
			return "admin/academicCalendar/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/academicCalendar/addNew";
		}
	}

	@PostMapping("/*")
	public String editAcademicCalendar(@ModelAttribute AcademicCalendar academicCalendar,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.academicCalendarValidator.validate(academicCalendar, result);
		
		if (result.hasErrors()) {
			return "admin/academicCalendar/edit";
		}
		
		try {
			TransactionResult tr = this.academicCalendarService.updateAcademicCalendar(academicCalendar, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/academicCalendar/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/academicCalendar/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/academicCalendar/edit/"+academicCalendar.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/academicCalendar/edit/"+academicCalendar.getId();
		}
	}
}

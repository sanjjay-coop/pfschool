package org.pf.school.controller.admin.academicCalendar;

import java.security.Principal;
import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/academicCalendar/addNew")
public class AcademicCalendarAddController extends AdminBaseController {

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
	
	@GetMapping
	public String academicCalendarAdd(Model model) {
		
		AcademicCalendar academicCalendar = new AcademicCalendar();
		
		model.addAttribute(academicCalendar);
		
		return "admin/academicCalendar/addNew";
	}
	
	@PostMapping
	public String academicCalendarAdd(@ModelAttribute AcademicCalendar academicCalendar,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.academicCalendarValidator.validate(academicCalendar, result);
		
		if (result.hasErrors()) {
			return "admin/academicCalendar/addNew";
		}
		
		try {
			TransactionResult tr = this.academicCalendarService.addAcademicCalendar(academicCalendar, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/academicCalendar/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/academicCalendar/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/academicCalendar/addNew";
		}
	}
}


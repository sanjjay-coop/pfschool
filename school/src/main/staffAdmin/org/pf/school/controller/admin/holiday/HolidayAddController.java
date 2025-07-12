package org.pf.school.controller.admin.holiday;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Holiday;
import org.pf.school.service.HolidayService;
import org.pf.school.validator.HolidayValidator;
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
@RequestMapping(value = "/admin/holiday/addNew")
public class HolidayAddController extends AdminBaseController {

	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private HolidayValidator holidayValidator;
	
	@GetMapping
	public String holidayAdd(Model model) {
		
		Holiday holiday = new Holiday();
		
		model.addAttribute(holiday);
		
		return "admin/holiday/addNew";
	}
	
	@PostMapping
	public String holidayAdd(@ModelAttribute Holiday holiday,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.holidayValidator.validate(holiday, result);
		
		if (result.hasErrors()) {
			return "admin/holiday/addNew";
		}
		
		try {
			TransactionResult tr = this.holidayService.addHoliday(holiday, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/holiday/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/holiday/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/holiday/addNew";
		}
	}
}

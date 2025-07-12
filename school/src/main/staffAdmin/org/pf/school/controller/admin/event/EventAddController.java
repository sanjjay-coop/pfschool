package org.pf.school.controller.admin.event;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Event;
import org.pf.school.service.EventService;
import org.pf.school.validator.EventValidator;
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
@RequestMapping(value = "/admin/event/addNew")
public class EventAddController extends AdminBaseController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventValidator eventValidator;
	
	@GetMapping
	public String eventAdd(Model model) {
		
		Event event = new Event();
		
		model.addAttribute(event);
		
		return "admin/event/addNew";
	}
	
	@PostMapping
	public String eventAdd(@ModelAttribute Event event,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.eventValidator.validate(event, result);
		
		if (result.hasErrors()) {
			return "admin/event/addNew";
		}
		
		try {
			TransactionResult tr = this.eventService.addEvent(event, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/event/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/event/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/event/addNew";
		}
	}
}


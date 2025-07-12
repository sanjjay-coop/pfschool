package org.pf.school.controller.admin.event;

import java.security.Principal;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/event/edit")
public class EventEditController extends AdminBaseController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventValidator eventValidator;
	
	@GetMapping("/{id}")
	public String editEvent(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Event event = (Event) this.eventService.getById(id);
			
			if (event == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/event/addNew";
			}
	
			model.addAttribute("event", event);
			
			return "admin/event/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/event/addNew";
		}
	}

	@PostMapping("/*")
	public String editEvent(@ModelAttribute Event event,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.eventValidator.validate(event, result);
		
		if (result.hasErrors()) {
			return "admin/event/edit";
		}
		
		try {
			TransactionResult tr = this.eventService.updateEvent(event, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/event/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/event/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/event/edit/"+event.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/event/edit/"+event.getId();
		}
	}
}

package org.pf.school.controller.admin.event;

import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Event;
import org.pf.school.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/event/view")
public class EventViewController extends AdminBaseController {

	@Autowired
	private EventService eventService;
	
	@GetMapping("/{id}")
	public String editEvent(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Event event = (Event) this.eventService.getById(id);
			
			if (event == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/event/list/current";
			}
	
			model.addAttribute("event", event);
			
			return "admin/event/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/event/list/current";
		}
	}
}

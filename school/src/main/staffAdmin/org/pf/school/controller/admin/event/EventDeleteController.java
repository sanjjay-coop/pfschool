package org.pf.school.controller.admin.event;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/event/delete")
public class EventDeleteController extends AdminBaseController {

	@Autowired
	private EventService eventService;

	@GetMapping("/{id}")
	public String deleteEvent(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			TransactionResult tr = this.eventService.deleteEvent(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				
			}
			return "redirect:/admin/event/list/current";
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/admin/event/list/current";
		}
		
	}
}



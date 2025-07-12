package org.pf.school.controller.library.title;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.controller.library.LibraryBaseController;
import org.pf.school.model.library.CheckOut;
import org.pf.school.service.library.CirculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/library/title/checkIn")
public class TitleCheckInController extends LibraryBaseController {

	@Autowired
	private CirculationService circulationService;
	
	@GetMapping("/{id}")
	public String viewTitle(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {

		UUID titleId = null;
		
		try {
			CheckOut checkOut = (CheckOut) this.circulationService.getCheckOutById(id);
			
			if (checkOut == null) {
				reat.addFlashAttribute("message", "Title is not checked out.");
				return "redirect:/library";
			}
			
			titleId = checkOut.getTitle().getId();
			
			circulationService.addCheckIn(checkOut, principal.getName());

			reat.addFlashAttribute("message", "Title returned successfully.");
			return "redirect:/library/title/view/" + titleId;
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/library/title/view/" + titleId;
		} 
	}
}


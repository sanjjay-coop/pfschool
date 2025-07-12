package org.pf.school.controller.library.titleType;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.library.LibraryBaseController;
import org.pf.school.service.library.TitleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/library/titleType/delete")
public class TitleTypeDeleteController extends LibraryBaseController {

	@Autowired
	private TitleTypeService titleTypeService;

	@GetMapping("/{id}")
	public String deleteTitleType(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			TransactionResult tr = this.titleTypeService.deleteTitleType(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/library/titleType/addNew";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/library/titleType/addNew";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/library/titleType/addNew";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/library/titleType/addNew";
			
		}
	}
}

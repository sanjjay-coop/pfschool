package org.pf.school.controller.library.library;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.library.LibraryBaseController;
import org.pf.school.model.library.Library;
import org.pf.school.service.library.LibraryService;
import org.pf.school.validator.library.edit.LibraryEditValidator;
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
@RequestMapping("/library/library/edit")
public class LibraryEditController extends LibraryBaseController {

	@Autowired
	private LibraryService libraryService;
	
	@Autowired
	private LibraryEditValidator libraryEditValidator;
		
	@GetMapping("/{id}")
	public String editLibrary(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Library library = (Library) this.libraryService.getById(id);
			
			if (library == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/library/library/addNew";
			}
	
			model.addAttribute("library", library);
			
			return "library/library/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/library/library/addNew";
		}
	}

	@PostMapping("/*")
	public String editLibrary(@ModelAttribute Library library,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.libraryEditValidator.validate(library, result);
		
		if (result.hasErrors()) {
			return "library/library/edit";
		}
		
		try {
			TransactionResult tr = this.libraryService.updateLibrary(library, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/library/library/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/library/library/addNew";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/library/library/edit/"+library.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/library/library/edit/"+library.getId();
		}
	}
}

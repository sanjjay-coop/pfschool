package org.pf.school.controller.library.library;

import java.security.Principal;
import java.util.List;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.library.LibraryBaseController;
import org.pf.school.model.library.Library;
import org.pf.school.repository.library.LibraryRepo;
import org.pf.school.service.library.LibraryService;
import org.pf.school.validator.library.add.LibraryAddValidator;
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
@RequestMapping(value = "/library/library/addNew")
public class LibraryAddController extends LibraryBaseController {

	@Autowired
	private LibraryRepo libraryRepo;
	
	@Autowired
	private LibraryService libraryService;
	
	@Autowired
	private LibraryAddValidator libraryAddValidator;
	
	@ModelAttribute("listLibrary")
	public List<Library> getListLibrary(){
		return (List<Library>) this.libraryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String libraryAdd(Model model) {
		
		Library library = new Library();
		
		model.addAttribute(library);
		
		return "library/library/addNew";
	}
	
	@PostMapping
	public String libraryAdd(@ModelAttribute Library library,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.libraryAddValidator.validate(library, result);
		
		if (result.hasErrors()) {
			return "library/library/addNew";
		}
		
		try {
			TransactionResult tr = this.libraryService.addLibrary(library, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "library/library/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/library/library/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "library/library/addNew";
		}
	}
}

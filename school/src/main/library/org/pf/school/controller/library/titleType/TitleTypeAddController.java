package org.pf.school.controller.library.titleType;

import java.security.Principal;
import java.util.List;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.library.LibraryBaseController;
import org.pf.school.model.library.TitleType;
import org.pf.school.repository.library.TitleTypeRepo;
import org.pf.school.service.library.TitleTypeService;
import org.pf.school.validator.library.add.TitleTypeAddValidator;
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
@RequestMapping(value = "/library/titleType/addNew")
public class TitleTypeAddController extends LibraryBaseController {

	@Autowired
	private TitleTypeRepo titleTypeRepo;
	
	@Autowired
	private TitleTypeService titleTypeService;
	
	@Autowired
	private TitleTypeAddValidator titleTypeAddValidator;
	
	@ModelAttribute("listTitleType")
	public List<TitleType> getListTitleType(){
		return (List<TitleType>) this.titleTypeRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String titleTypeAdd(Model model) {
		
		TitleType titleType = new TitleType();
		
		model.addAttribute(titleType);
		
		return "library/titleType/addNew";
	}
	
	@PostMapping
	public String titleTypeAdd(@ModelAttribute TitleType titleType,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.titleTypeAddValidator.validate(titleType, result);
		
		if (result.hasErrors()) {
			return "library/titleType/addNew";
		}
		
		try {
			TransactionResult tr = this.titleTypeService.addTitleType(titleType, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "library/titleType/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/library/titleType/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "library/titleType/addNew";
		}
	}
}

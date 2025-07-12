package org.pf.school.controller.admin.document;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Document;
import org.pf.school.service.DocumentService;
import org.pf.school.validator.add.DocumentAddValidator;
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
@RequestMapping(value = "/admin/document/addNew")
public class DocumentAddController extends AdminBaseController {

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private DocumentAddValidator documentAddValidator;
	
	@GetMapping
	public String documentAdd(Model model) {
		
		Document document = new Document();
		
		model.addAttribute(document);
		
		return "admin/document/addNew";
	}
	
	@PostMapping
	public String documentAdd(@ModelAttribute Document document,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.documentAddValidator.validate(document, result);
		
		if (result.hasErrors()) {
			return "admin/document/addNew";
		}
		
		try {
			TransactionResult tr = this.documentService.addDocument(document, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/document/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/document/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/document/addNew";
		}
	}
}

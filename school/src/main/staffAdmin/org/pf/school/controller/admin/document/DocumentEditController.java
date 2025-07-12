package org.pf.school.controller.admin.document;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Document;
import org.pf.school.service.DocumentService;
import org.pf.school.validator.edit.DocumentEditValidator;
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
@RequestMapping("/admin/document/edit")
public class DocumentEditController extends AdminBaseController {

	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private DocumentEditValidator documentEditValidator;
	
	@GetMapping("/{id}")
	public String editDocument(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Document document = (Document) this.documentService.getById(id);
			
			if (document == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/document/addNew";
			}
	
			model.addAttribute("document", document);
			
			return "admin/document/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/document/addNew";
		}
	}

	@PostMapping("/*")
	public String editDocument(@ModelAttribute Document document,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.documentEditValidator.validate(document, result);
		
		if (result.hasErrors()) {
			return "admin/document/edit";
		}
		
		try {
			TransactionResult tr = this.documentService.updateDocument(document, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/document/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/document/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/document/edit/"+document.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/document/edit/"+document.getId();
		}
	}
}

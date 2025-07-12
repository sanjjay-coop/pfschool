package org.pf.school.controller.manager.quotation;


import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.manager.ManagerBaseController;
import org.pf.school.model.Quotation;
import org.pf.school.service.QuotationService;
import org.pf.school.validator.QuotationValidator;
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
@RequestMapping("/manager/quotation/edit")
public class QuotationEditController extends ManagerBaseController {

	@Autowired
	private QuotationService quotationService;
	
	@Autowired
	private QuotationValidator quotationValidator;
	
	@GetMapping("/{id}")
	public String editQuotation(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Quotation quotation = (Quotation) this.quotationService.getById(id);
			
			if (quotation == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/quotation/addNew";
			}
	
			model.addAttribute("quotation", quotation);
			
			return "manager/quotation/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/quotation/addNew";
		}
	}

	@PostMapping("/*")
	public String editQuotation(@ModelAttribute Quotation quotation,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.quotationValidator.validate(quotation, result);
		
		if (result.hasErrors()) {
			return "manager/quotation/edit";
		}
		
		try {
			TransactionResult tr = this.quotationService.updateQuotation(quotation, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/quotation/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/quotation/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/quotation/edit/"+quotation.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/quotation/edit/"+quotation.getId();
		}
	}
}


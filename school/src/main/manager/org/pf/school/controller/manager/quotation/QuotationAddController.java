package org.pf.school.controller.manager.quotation;

import java.security.Principal;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/manager/quotation/addNew")
public class QuotationAddController extends ManagerBaseController {

	@Autowired
	private QuotationService quotationService;
	
	@Autowired
	private QuotationValidator quotationValidator;
	
	@GetMapping
	public String quotationAdd(Model model) {
		
		Quotation quotation = new Quotation();
		
		model.addAttribute(quotation);
		
		return "manager/quotation/addNew";
	}
	
	@PostMapping
	public String quotationAdd(@ModelAttribute Quotation quotation,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.quotationValidator.validate(quotation, result);
		
		if (result.hasErrors()) {
			return "manager/quotation/addNew";
		}
		
		try {
			TransactionResult tr = this.quotationService.addQuotation(quotation, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/quotation/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/quotation/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/quotation/addNew";
		}
	}
}


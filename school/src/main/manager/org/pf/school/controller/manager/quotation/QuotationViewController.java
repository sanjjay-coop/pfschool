package org.pf.school.controller.manager.quotation;

import java.util.UUID;

import org.pf.school.controller.manager.ManagerBaseController;
import org.pf.school.model.Quotation;
import org.pf.school.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/quotation/view")
public class QuotationViewController extends ManagerBaseController {

	@Autowired
	private QuotationService quotationService;
	
	@GetMapping("/{id}")
	public String editQuotation(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Quotation quotation = (Quotation) this.quotationService.getById(id);
			
			if (quotation == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/quotation/list/current";
			}
	
			model.addAttribute("quotation", quotation);
			
			return "manager/quotation/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/quotation/list/current";
		}
	}
}
package org.pf.school.controller.accounts.expenditure;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.accounts.Expenditure;
import org.pf.school.service.accounts.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accounts/expenditure/delete")
public class ExpenditureDeleteController extends AccountsBaseController {

	@Autowired
	private ExpenditureService expenditureService;

	@GetMapping("/{id}")
	public String deleteExpenditure(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		Expenditure exp = (Expenditure) this.expenditureService.getById(id);
		
		if (exp!=null && !exp.getDirect()) {
			reat.addFlashAttribute("message", "Record cannot be deleted through this module.");
			return "redirect:/accounts/expenditure/listRecent";
		}
		try {
			
			TransactionResult tr = this.expenditureService.deleteExpenditure(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/accounts/expenditure/addNew";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/accounts/expenditure/addNew";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/accounts/expenditure/addNew";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/accounts/expenditure/addNew";
			
		}
	}
}


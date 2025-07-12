package org.pf.school.controller.accounts.income;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.accounts.Income;
import org.pf.school.service.accounts.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accounts/income/delete")
public class IncomeDeleteController extends AccountsBaseController {

	@Autowired
	private IncomeService incomeService;

	@GetMapping("/{id}")
	public String deleteIncome(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		Income inc = (Income) this.incomeService.getById(id);
		
		if (inc == null) {
			reat.addFlashAttribute("message", "Record cannot be deleted through this module.");
			return "redirect:/accounts/income/listRecent";
		}
		
		if (!inc.getDirect()) {
			reat.addFlashAttribute("message", "Record cannot be deleted through this module.");
			return "redirect:/accounts/income/listRecent";
		}
		
		try {
			
			TransactionResult tr = this.incomeService.deleteIncome(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/accounts/income/addNew";
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/accounts/income/addNew";
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/accounts/income/addNew";
				
			}
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/accounts/income/addNew";
			
		}
	}
}


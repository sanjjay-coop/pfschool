package org.pf.school.controller.accounts.income;

import java.security.Principal;
import java.util.List;

import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.accounts.Income;
import org.pf.school.repository.accounts.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IncomeListRecentController extends AccountsBaseController {

	@Autowired
	private IncomeRepo incomeRepo;

	@GetMapping("/accounts/income/listRecent")
	public String listRecent(Model model, RedirectAttributes reat, Principal principal) {

		List<Income> listIncome = this.incomeRepo.listIncomeRecent();
		
		model.addAttribute("listIncome", listIncome);
		
		return "accounts/income/listRecent";
	}
}


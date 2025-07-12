package org.pf.school.controller.accounts.headOfAccount;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.accounts.HeadOfAccount;
import org.pf.school.service.accounts.HeadOfAccountService;
import org.pf.school.validator.accounts.add.HeadOfAccountAddValidator;
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
@RequestMapping(value = "/accounts/headOfAccount/addNew")
public class HeadOfAccountAddController extends AccountsBaseController {

	@Autowired
	private HeadOfAccountService headOfAccountService;
	
	@Autowired
	private HeadOfAccountAddValidator headOfAccountAddValidator;
	
	@GetMapping
	public String headOfAccountAdd(Model model) {
		
		HeadOfAccount headOfAccount = new HeadOfAccount();
		
		model.addAttribute(headOfAccount);
		
		return "accounts/headOfAccount/addNew";
	}
	
	@PostMapping
	public String headOfAccountAdd(@ModelAttribute HeadOfAccount headOfAccount,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.headOfAccountAddValidator.validate(headOfAccount, result);
		
		if (result.hasErrors()) {
			return "accounts/headOfAccount/addNew";
		}
		
		try {
			TransactionResult tr = this.headOfAccountService.addHeadOfAccount(headOfAccount, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "accounts/headOfAccount/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/accounts/headOfAccount/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "accounts/headOfAccount/addNew";
		}
	}
}

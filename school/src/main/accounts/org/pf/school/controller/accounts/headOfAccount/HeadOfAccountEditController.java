package org.pf.school.controller.accounts.headOfAccount;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.accounts.HeadOfAccount;
import org.pf.school.service.accounts.HeadOfAccountService;
import org.pf.school.validator.accounts.edit.HeadOfAccountEditValidator;
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
@RequestMapping("/accounts/headOfAccount/edit")
public class HeadOfAccountEditController extends AccountsBaseController {

	@Autowired
	private HeadOfAccountService headOfAccountService;
	
	@Autowired
	private HeadOfAccountEditValidator headOfAccountEditValidator;
	
	@GetMapping("/{id}")
	public String editHeadOfAccount(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			HeadOfAccount headOfAccount = (HeadOfAccount) this.headOfAccountService.getById(id);
			
			if (headOfAccount == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/accounts/headOfAccount/addNew";
			}
	
			model.addAttribute("headOfAccount", headOfAccount);
			
			return "accounts/headOfAccount/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/accounts/headOfAccount/addNew";
		}
	}

	@PostMapping("/*")
	public String editHeadOfAccount(@ModelAttribute HeadOfAccount headOfAccount,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.headOfAccountEditValidator.validate(headOfAccount, result);
		
		if (result.hasErrors()) {
			return "accounts/headOfAccount/edit";
		}
		
		try {
			TransactionResult tr = this.headOfAccountService.updateHeadOfAccount(headOfAccount, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/accounts/headOfAccount/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/accounts/headOfAccount/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/accounts/headOfAccount/edit/"+headOfAccount.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/accounts/headOfAccount/edit/"+headOfAccount.getId();
		}
	}
}

package org.pf.school.controller.accounts.expenditure;

import java.security.Principal;
import java.util.List;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.accounts.Expenditure;
import org.pf.school.model.accounts.HeadOfAccount;
import org.pf.school.repository.accounts.HeadOfAccountRepo;
import org.pf.school.service.accounts.ExpenditureService;
import org.pf.school.validator.accounts.ExpenditureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/accounts/expenditure/addNew")
public class ExpenditureAddController extends AccountsBaseController {
	
	@Autowired
	private ExpenditureService expenditureService;
	
	@Autowired
	private ExpenditureValidator expenditureValidator;
	
	@Autowired
	private HeadOfAccountRepo headOfAccountRepo;
	
	@ModelAttribute("listHeadOfAccount")
	public List<HeadOfAccount> getListHeadOfAccount(){
		return (List<HeadOfAccount>) this.headOfAccountRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping
	public String expenditureAdd(Model model) {
		
		Expenditure expenditure = new Expenditure();
		
		model.addAttribute(expenditure);
		
		return "accounts/expenditure/addNew";
	}
	
	@PostMapping
	public String expenditureAdd(@ModelAttribute Expenditure expenditure,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.expenditureValidator.validate(expenditure, result);
		
		if (result.hasErrors()) {
			return "accounts/expenditure/addNew";
		}
		
		expenditure.setDirect(true);
		
		try {
			TransactionResult tr = this.expenditureService.addExpenditure(expenditure, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "accounts/expenditure/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/accounts/expenditure/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "accounts/expenditure/addNew";
		}
	}
}

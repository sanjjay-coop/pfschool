package org.pf.school.controller.accounts.staff;

import java.util.UUID;

import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.Staff;
import org.pf.school.repository.accounts.ExpenditureRepo;
import org.pf.school.repository.accounts.IncomeRepo;
import org.pf.school.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accounts/staff/view")
public class AccountsStaffViewController extends AccountsBaseController {

	@Autowired
	private ExpenditureRepo expenditureRepo;
	
	@Autowired
	private IncomeRepo incomeRepo;
	
	@Autowired
	private StaffService staffService;
	
	@GetMapping("/{id}")
	public String editStaff(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Staff staff = (Staff) this.staffService.getById(id);
			
			if (staff == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/accounts/staff/list/current";
			}
	
			model.addAttribute("staff", staff);
			model.addAttribute("listReceipt", this.incomeRepo.findByMemberOrderByTransactionDateDesc(staff.getMember()));
			model.addAttribute("listPayment", this.expenditureRepo.findByMemberOrderByTransactionDateDesc(staff.getMember()));
			
			return "accounts/staff/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/accounts/staff/list/current";
		}
	}
}
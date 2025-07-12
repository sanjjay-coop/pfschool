package org.pf.school.controller.dashboard.payments;

import java.security.Principal;
import java.util.List;

import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.model.Member;
import org.pf.school.model.accounts.Income;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.accounts.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/payments/list")
public class DashboardPaymentsController extends DashboardBaseController {

	@Autowired
	IncomeRepo incomeRepo;
	
	@Autowired
	MemberRepo memberRepo;
	
	@GetMapping
	public String getPayments(Model model, RedirectAttributes reat, Principal principal) {
		
		Member member = this.memberRepo.findByUid(principal.getName());
		
		if (member == null) {
			reat.addFlashAttribute("message", "Member record not found.");
			return "redirect:/dashboard";
		}
		
		List<Income> listIncome = this.incomeRepo.findByMemberOrderByTransactionDateDesc(member);
		
		model.addAttribute("listIncome", listIncome);
		
		return "dashboard/payments/list";
	}
}

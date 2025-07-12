package org.pf.school.controller.dashboard.receipts;

import java.security.Principal;
import java.util.List;

import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.model.Member;
import org.pf.school.model.accounts.Expenditure;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.accounts.ExpenditureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/receipts/list")
public class DashboardReceiptsController extends DashboardBaseController {

	@Autowired
	ExpenditureRepo expenditureRepo;
	
	@Autowired
	MemberRepo memberRepo;
	
	@GetMapping
	public String getReceipts(Model model, RedirectAttributes reat, Principal principal) {
		
		Member member = this.memberRepo.findByUid(principal.getName());
		
		if (member == null) {
			reat.addFlashAttribute("message", "Member record not found.");
			return "redirect:/dashboard";
		}
		
		List<Expenditure> listExpenditure = this.expenditureRepo.findByMemberOrderByTransactionDateDesc(member);
		
		model.addAttribute("listExpenditure", listExpenditure);
		
		return "dashboard/receipts/list";
	}
}

package org.pf.school.controller.accounts.student.receipt;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.Student;
import org.pf.school.model.accounts.Income;
import org.pf.school.repository.StudentRepo;
import org.pf.school.service.accounts.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/accounts/student/receipt/delete")
public class StudentReceiptDeleteController extends AccountsBaseController {

	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	IncomeService incomeService;
	
	@GetMapping("/{id}")
	public String deleteStudentReceipt(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		Income income = (Income) this.incomeService.getById(id);
		
		if (income == null) {
			reat.addFlashAttribute("mesage", "No receipt record is found.");
			return "redirect:/accounts/student/list";
		}
		
		if (income.getMember().getUid().equals(principal.getName())) {
			reat.addFlashAttribute("mesage", "Cannot delete self records.");
			return "redirect:/accounts/student/list";
		}
		
		Student student = this.studentRepo.findByMember(income.getMember());
		
		try {
			
			TransactionResult tr = this.incomeService.deleteIncome(id, principal.getName());
			
			if (tr == null ) {
				reat.addFlashAttribute("message", "Record could not be deleted.");
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record deleted successfully.");
			} else {
				reat.addFlashAttribute("message", tr.getMessage());
			}
			return "redirect:/accounts/student/view/" + student.getId();
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/accounts/student/view/" + student.getId();
		}
	}
}


package org.pf.school.controller.accounts.student.payment;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.Student;
import org.pf.school.model.accounts.Expenditure;
import org.pf.school.repository.StudentRepo;
import org.pf.school.service.accounts.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/accounts/student/payment/delete")
public class StudentPaymentDeleteController extends AccountsBaseController {

	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	ExpenditureService expenditureService;
	
	@GetMapping("/{id}")
	public String deleteStudentPayment(@PathVariable Long id, Model model,
			RedirectAttributes reat, Principal principal) {

		Expenditure expenditure = (Expenditure) this.expenditureService.getById(id);
		
		if (expenditure == null) {
			reat.addFlashAttribute("message", "No payment record is found.");
			return "redirect:/accounts/student/list";
		}
		
		if (expenditure.getMember().getUid().equals(principal.getName())) {
			reat.addFlashAttribute("message", "Cannot delete self records.");
			return "redirect:/accounts/student/list";
		}
		
		Student student = this.studentRepo.findByMember(expenditure.getMember());
		
		try {
			
			TransactionResult tr = this.expenditureService.deleteExpenditure(id, principal.getName());
			
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

package org.pf.school.controller.accounts.student;

import java.util.UUID;

import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.Student;
import org.pf.school.repository.accounts.ExpenditureRepo;
import org.pf.school.repository.accounts.IncomeRepo;
import org.pf.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accounts/student/view")
public class AccountsStudentViewController extends AccountsBaseController {

	@Autowired
	private ExpenditureRepo expenditureRepo;
	
	@Autowired
	private IncomeRepo incomeRepo;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/{id}")
	public String editStudent(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Student student = (Student) this.studentService.getById(id);
			
			if (student == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/accounts/student/list/current";
			}
	
			model.addAttribute("student", student);
			model.addAttribute("listReceipt", this.incomeRepo.findByMemberOrderByTransactionDateDesc(student.getMember()));
			model.addAttribute("listPayment", this.expenditureRepo.findByMemberOrderByTransactionDateDesc(student.getMember()));
			
			return "accounts/student/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/accounts/student/list/current";
		}
	}
}

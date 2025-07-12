package org.pf.school.controller.accounts.student.receipt;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.accounts.AccountsBaseController;
import org.pf.school.model.Student;
import org.pf.school.model.accounts.HeadOfAccount;
import org.pf.school.model.accounts.Income;
import org.pf.school.repository.StudentRepo;
import org.pf.school.repository.accounts.HeadOfAccountRepo;
import org.pf.school.service.StudentService;
import org.pf.school.service.accounts.IncomeService;
import org.pf.school.validator.accounts.ReceiptValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping(value="/accounts/student/receive/payment")
public class StudentReceiptController extends AccountsBaseController {

	@Autowired
	StudentService studentService;
	
	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	IncomeService incomeService;
	
	@Autowired
	ReceiptValidator receiptValidator;
	
	@Autowired
	private HeadOfAccountRepo headOfAccountRepo;
	
	@ModelAttribute("listHeadOfAccount")
	public List<HeadOfAccount> getListHeadOfAccount(){
		return (List<HeadOfAccount>) this.headOfAccountRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping("/{id}")
	public String receivePayment(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {
	
		Student student = (Student) this.studentService.getById(id);
		
		if (student == null) {
			reat.addFlashAttribute("message", "No such a record is found.");
			return "redirect:/accounts/student/list";
		}
		
		Income income = new Income();
		
		income.setMember(student.getMember());
		
		income.setReceivedFrom(student.getLabel());
		
		model.addAttribute("income", income);
		model.addAttribute("student", student);
		
		return "accounts/student/receipt";
	}
	
	@PostMapping("/*")
	public String receivePayment(@ModelAttribute Income income,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		if (income.getMember()==null) {
			reat.addFlashAttribute("message", "Student record is not found.");
			return "redirect:/accounts/student/list/current";
		}
	
		this.receiptValidator.validate(income, result);
		
		Student student = (Student) this.studentRepo.findByMember(income.getMember());
		
		if (student==null) {
			reat.addFlashAttribute("message", "Student record is not found.");
			return "redirect:/accounts/student/list/current";
		}
		
		if (result.hasErrors()) {
			model.addAttribute("student", student);
			return "accounts/student/receipt";
		}
		
		income.setDirect(false);
		
		try {
			TransactionResult tr = this.incomeService.addIncome(income, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/accounts/student/view/" + student.getId();
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Payment received successfully.");
					return "redirect:/accounts/student/view/" + student.getId();
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					System.out.println(tr.getMessage());
					return "redirect:/accounts/student/view/" + student.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			System.out.println(e.getMessage());
			return "redirect:/accounts/student/view/" + student.getId();
		}
	}
}

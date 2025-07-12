package org.pf.school.controller.admin.sessionDetail;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.model.Student;
import org.pf.school.repository.StudentRepo;
import org.pf.school.service.SessionDetailService;
import org.pf.school.service.SessionDetailStudentService;
import org.pf.school.validator.SessionDetailStudentValidator;
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
@RequestMapping("/admin/sessionDetail/register/student")
public class RegisterStudentController extends AdminBaseController {

	@Autowired
	SessionDetailService sessionDetailService;
	
	@Autowired
	SessionDetailStudentService sdsService; 
	
	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	SessionDetailStudentValidator sessionDetailStudentValidator;
	
	@GetMapping("/{id}")
	public String registerStudent(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			SessionDetail sessionDetail = (SessionDetail) this.sessionDetailService.getById(id);
			
			if (sessionDetail == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/sessionDetail/list/current";
			}
	
			SessionDetailStudent sessionDetailStudent = new SessionDetailStudent();
			
			sessionDetailStudent.setSessionDetail(sessionDetail);
			
			model.addAttribute("sessionDetailStudent", sessionDetailStudent);
			
			List<Student> listStudent = new ArrayList<Student>();
			
			List<UUID> listStudentRegistered = this.sdsService.getListStudentRegistered();
			
			if (listStudentRegistered.isEmpty()) {
				listStudent = this.studentRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
			} else {
				listStudent = this.studentRepo.findByIdNotInOrderByNameAsc(this.sdsService.getListStudentRegistered());
			}
			
			if (listStudent.isEmpty()) {
				reat.addFlashAttribute("message", "No student is available to register.");
				return "redirect:/admin/sessionDetail/view/" + id;
			}
			
			model.addAttribute("listStudent", listStudent);
			
			return "admin/sessionDetail/register/student";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/sessionDetail/list/current";
		}
	}
	
	@PostMapping("/*")
	public String registerStudent(@ModelAttribute SessionDetailStudent sessionDetailStudent,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.sessionDetailStudentValidator.validate(sessionDetailStudent, result);
		
		if (result.hasErrors()) {
			return "admin/sessionDetail/register/student";
		}
		
		try {
			TransactionResult tr = this.sdsService.addSessionDetailStudent(sessionDetailStudent, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/sessionDetail/view/" + sessionDetailStudent.getSessionDetail().getId();
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/sessionDetail/view/" + sessionDetailStudent.getSessionDetail().getId();
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/sessionDetail/view/" + sessionDetailStudent.getSessionDetail().getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/sessionDetail/view/" + sessionDetailStudent.getSessionDetail().getId();
		}
	}
}

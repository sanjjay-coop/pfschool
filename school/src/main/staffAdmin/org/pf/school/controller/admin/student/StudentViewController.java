package org.pf.school.controller.admin.student;

import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Student;
import org.pf.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/student/view")
public class StudentViewController extends AdminBaseController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping("/{id}")
	public String editStudent(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Student student = (Student) this.studentService.getById(id);
			
			if (student == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/student/list/current";
			}
	
			model.addAttribute("student", student);
			
			return "admin/student/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/student/list/current";
		}
	}
}


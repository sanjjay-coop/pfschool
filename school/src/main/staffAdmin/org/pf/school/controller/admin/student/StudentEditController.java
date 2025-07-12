package org.pf.school.controller.admin.student;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Category;
import org.pf.school.model.Gender;
import org.pf.school.model.SchoolClass;
import org.pf.school.model.Student;
import org.pf.school.repository.CategoryRepo;
import org.pf.school.repository.GenderRepo;
import org.pf.school.repository.SchoolClassRepo;
import org.pf.school.service.StudentService;
import org.pf.school.validator.edit.StudentEditValidator;
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
@RequestMapping("/admin/student/edit")
public class StudentEditController extends AdminBaseController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentEditValidator studentEditValidator;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private GenderRepo genderRepo;
	
	@Autowired
	private SchoolClassRepo schoolClassRepo;
	
	@ModelAttribute("listCategory")
	public List<Category> getListCategory(){
		return this.categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listGender")
	public List<Gender> getListGender(){
		return this.genderRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listSchoolClass")
	public List<SchoolClass> getListSchoolClass(){
		return this.schoolClassRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping("/{id}")
	public String editStudent(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Student student = (Student) this.studentService.getById(id);
			
			if (student == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/student/addNew";
			}
	
			model.addAttribute("student", student);
			
			return "admin/student/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/student/addNew";
		}
	}

	@PostMapping("/*")
	public String editStudent(@ModelAttribute Student student,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.studentEditValidator.validate(student, result);
		
		if (result.hasErrors()) {
			return "admin/student/edit";
		}
		
		try {
			TransactionResult tr = this.studentService.updateStudent(student, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/student/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/student/view/"+student.getId();
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/student/edit/"+student.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/student/edit/"+student.getId();
		}
	}
}


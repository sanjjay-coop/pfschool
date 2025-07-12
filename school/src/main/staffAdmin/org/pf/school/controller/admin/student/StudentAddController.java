package org.pf.school.controller.admin.student;

import java.security.Principal;
import java.util.List;

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
import org.pf.school.validator.add.StudentAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/student/addNew")
public class StudentAddController extends AdminBaseController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentAddValidator studentAddValidator;
	
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
	
	@GetMapping
	public String studentAdd(Model model) {
		
		Student student = new Student();
		
		model.addAttribute(student);
		
		return "admin/student/addNew";
	}
	
	@PostMapping
	public String studentAdd(@ModelAttribute Student student,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.studentAddValidator.validate(student, result);
		
		if (result.hasErrors()) {
			return "admin/student/addNew";
		}
		
		try {
			TransactionResult tr = this.studentService.addStudent(student, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/student/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/student/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/student/addNew";
		}
	}
}
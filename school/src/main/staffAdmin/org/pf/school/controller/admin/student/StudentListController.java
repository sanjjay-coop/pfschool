package org.pf.school.controller.admin.student;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Student;
import org.pf.school.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/student")
public class StudentListController extends AdminBaseController {
	
	@Autowired
	private StudentRepo studentRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listStudent(@ModelAttribute Student student, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("adminSearch_student", student);
				
			return "redirect:/admin/student/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listStudent(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by("name").ascending());
			
			Page<Student> page;
			
			Student obj = (Student) request.getSession().getAttribute("adminSearch_student");
			
			if (obj == null) {
				page = this.studentRepo.findAll(pageable);
				obj = new Student();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.studentRepo.findAll(pageable);
				} else {
					page = this.studentRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("adminSearch_student", obj);
			model.addAttribute("student", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listStudent", page.getContent());
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalRecords", page.getTotalElements());
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listStudentAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listStudentAdmin_totalPages", totalPages);
			
			return "admin/student/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listStudent(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listStudentAdmin_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listStudentAdmin_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/student/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by("name").ascending());
			
			Page<Student> page;
			
			Student obj = (Student) request.getSession().getAttribute("adminSearch_student");
			
			if (obj == null) {
				page = this.studentRepo.findAll(pageable);
				obj = new Student();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.studentRepo.findAll(pageable);
				} else {
					page = this.studentRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("adminSearch_student", obj);
			model.addAttribute("student", obj);
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalRecords", page.getTotalElements());
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listStudentAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listStudentAdmin_totalPages", totalPages);
			
			model.addAttribute("listStudent", page.getContent());
			
			return "admin/student/list";
		
		} catch(Exception e) {
			return "redirect:/admin/student/list";
		}
	}
}

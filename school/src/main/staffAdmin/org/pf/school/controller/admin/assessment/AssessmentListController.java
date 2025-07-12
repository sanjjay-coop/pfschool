package org.pf.school.controller.admin.assessment;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Assessment;
import org.pf.school.repository.AssessmentRepo;
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
@RequestMapping("/admin/assessment")
public class AssessmentListController extends AdminBaseController {
	
	@Autowired
	private AssessmentRepo assessmentRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listAssessment(@ModelAttribute Assessment assessment, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("adminSearch_assessment", assessment);
				
			return "redirect:/admin/assessment/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listAssessment(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<Assessment> page;
			
			Assessment obj = (Assessment) request.getSession().getAttribute("adminSearch_assessment");
			
			if (obj == null) {
				page = this.assessmentRepo.findAll(pageable);
				obj = new Assessment();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.assessmentRepo.findAll(pageable);
				} else {
					page = this.assessmentRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("adminSearch_assessment", obj);
			model.addAttribute("assessment", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listAssessment", page.getContent());
			
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
			
			request.getSession().setAttribute("listAssessmentAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listAssessmentAdmin_totalPages", totalPages);
			
			return "admin/assessment/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listAssessment(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listAssessmentAdmin_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listAssessmentAdmin_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/assessment/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<Assessment> page;
			
			Assessment obj = (Assessment) request.getSession().getAttribute("adminSearch_assessment");
			
			if (obj == null) {
				page = this.assessmentRepo.findAll(pageable);
				obj = new Assessment();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.assessmentRepo.findAll(pageable);
				} else {
					page = this.assessmentRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("adminSearch_assessment", obj);
			model.addAttribute("assessment", obj);
			
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
			
			request.getSession().setAttribute("listAssessmentAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listAssessmentAdmin_totalPages", totalPages);
			
			model.addAttribute("listAssessment", page.getContent());
			
			return "admin/assessment/list";
		
		} catch(Exception e) {
			return "redirect:/admin/assessment/list";
		}
	}
}

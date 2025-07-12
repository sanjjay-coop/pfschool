package org.pf.school.controller.admin.schoolClass;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.SchoolClass;
import org.pf.school.repository.SchoolClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/schoolClass")
public class SchoolClassListController extends AdminBaseController {
	
	@Autowired
	private SchoolClassRepo schoolClassRepo;
	
	@GetMapping("/list")
	public String listSchoolClass(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "seqNumber"));
		
		Page<SchoolClass> page = this.schoolClassRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listSchoolClass", page.getContent());
		
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
		
		request.getSession().setAttribute("listSchoolClass_pageNumber", pageNumber);
		request.getSession().setAttribute("listSchoolClass_totalPages", totalPages);
		
		return "admin/schoolClass/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listSchoolClass(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listSchoolClass_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listSchoolClass_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/schoolClass/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "seqNumber"));
			
			Page<SchoolClass> page = this.schoolClassRepo.findAll(pageable);
			
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
			
			request.getSession().setAttribute("listSchoolClass_pageNumber", pageNumber);
			request.getSession().setAttribute("listSchoolClass_totalPages", totalPages);
			
			model.addAttribute("listSchoolClass", page.getContent());
			
			return "admin/schoolClass/list";
		
		} catch(Exception e) {
			return "redirect:/admin/schoolClass/list";
		}
	}
}


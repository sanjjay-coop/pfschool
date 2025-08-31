package org.pf.school.controller.admin.academicSession;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.AcademicSession;
import org.pf.school.repository.AcademicSessionRepo;
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
@RequestMapping("/admin/academicSession")
public class AcademicSessionListController extends AdminBaseController {
	
	@Autowired
	private AcademicSessionRepo academicSessionRepo;
	
	@GetMapping("/list")
	public String listAcademicSession(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
		
		Page<AcademicSession> page = this.academicSessionRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listAcademicSession", page.getContent());
		
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
		
		request.getSession().setAttribute("listAcademicSession_pageNumber", pageNumber);
		request.getSession().setAttribute("listAcademicSession_totalPages", totalPages);
		
		return "admin/academicSession/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listAcademicSession(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listAcademicSession_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listAcademicSession_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/academicSession/list";
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
			
			Page<AcademicSession> page = this.academicSessionRepo.findAll(pageable);
			
			totalPages = page.getTotalPages();
			
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
			
			request.getSession().setAttribute("listAcademicSession_pageNumber", pageNumber);
			request.getSession().setAttribute("listAcademicSession_totalPages", totalPages);
			
			model.addAttribute("listAcademicSession", page.getContent());
			
			return "admin/academicSession/list";
		
		} catch(Exception e) {
			return "redirect:/admin/academicSession/list";
		}
	}
}
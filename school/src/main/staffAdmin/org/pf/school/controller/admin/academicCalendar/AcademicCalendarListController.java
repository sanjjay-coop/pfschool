package org.pf.school.controller.admin.academicCalendar;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.AcademicCalendar;
import org.pf.school.repository.AcademicCalendarRepo;
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
@RequestMapping("/admin/academicCalendar")
public class AcademicCalendarListController extends AdminBaseController {
	
	@Autowired
	private AcademicCalendarRepo academicCalendarRepo;
	
	@GetMapping("/list")
	public String listAcademicCalendar(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "eventDate"));
		
		Page<AcademicCalendar> page = this.academicCalendarRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listAcademicCalendar", page.getContent());
		
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
		
		request.getSession().setAttribute("listAcademicCalendar_pageNumber", pageNumber);
		request.getSession().setAttribute("listAcademicCalendar_totalPages", totalPages);
		
		return "admin/academicCalendar/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listAcademicCalendar(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listAcademicCalendar_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listAcademicCalendar_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/academicCalendar/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "eventDate"));
			
			Page<AcademicCalendar> page = this.academicCalendarRepo.findAll(pageable);
			
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
			
			request.getSession().setAttribute("listAcademicCalendar_pageNumber", pageNumber);
			request.getSession().setAttribute("listAcademicCalendar_totalPages", totalPages);
			
			model.addAttribute("listAcademicCalendar", page.getContent());
			
			return "admin/academicCalendar/list";
		
		} catch(Exception e) {
			return "redirect:/admin/academicCalendar/list";
		}
	}
}

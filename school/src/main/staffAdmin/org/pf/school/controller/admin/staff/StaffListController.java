package org.pf.school.controller.admin.staff;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Staff;
import org.pf.school.repository.StaffRepo;
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
@RequestMapping("/admin/staff")
public class StaffListController extends AdminBaseController {
	
	@Autowired
	private StaffRepo staffRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listStaff(@ModelAttribute Staff staff, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("adminSearch_staff", staff);
				
			return "redirect:/admin/staff/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listStaff(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "name"));
			
			Page<Staff> page;
			
			Staff obj = (Staff) request.getSession().getAttribute("adminSearch_staff");
			
			if (obj == null) {
				page = this.staffRepo.findAll(pageable);
				obj = new Staff();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.staffRepo.findAll(pageable);
				} else {
					page = this.staffRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("adminSearch_staff", obj);
			model.addAttribute("staff", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listStaff", page.getContent());
			
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
			
			request.getSession().setAttribute("listStaffAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listStaffAdmin_totalPages", totalPages);
			
			return "admin/staff/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listStaff(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listStaffAdmin_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listStaffAdmin_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/staff/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "name"));
			
			Page<Staff> page;
			
			Staff obj = (Staff) request.getSession().getAttribute("adminSearch_staff");
			
			if (obj == null) {
				page = this.staffRepo.findAll(pageable);
				obj = new Staff();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.staffRepo.findAll(pageable);
				} else {
					page = this.staffRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("adminSearch_staff", obj);
			model.addAttribute("staff", obj);
			
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
			
			request.getSession().setAttribute("listStaffAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listStaffAdmin_totalPages", totalPages);
			
			model.addAttribute("listStaff", page.getContent());
			
			return "admin/staff/list";
		
		} catch(Exception e) {
			return "redirect:/admin/staff/list";
		}
	}
}


package org.pf.school.controller.admin.holiday;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Holiday;
import org.pf.school.repository.HolidayRepo;
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
@RequestMapping("/admin/holiday")
public class HolidayListController extends AdminBaseController {
	
	@Autowired
	private HolidayRepo holidayRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listHoliday(@ModelAttribute Holiday holiday, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("adminSearch_holiday", holiday);
				
			return "redirect:/admin/holiday/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listHoliday(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<Holiday> page;
			
			Holiday obj = (Holiday) request.getSession().getAttribute("adminSearch_holiday");
			
			if (obj == null) {
				page = this.holidayRepo.findAll(pageable);
				obj = new Holiday();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.holidayRepo.findAll(pageable);
				} else {
					page = this.holidayRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("adminSearch_holiday", obj);
			model.addAttribute("holiday", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listHoliday", page.getContent());
			
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
			
			request.getSession().setAttribute("listHolidayAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listHolidayAdmin_totalPages", totalPages);
			
			return "admin/holiday/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listHoliday(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listHolidayAdmin_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listHolidayAdmin_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/holiday/list";
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
			
			Page<Holiday> page;
			
			Holiday obj = (Holiday) request.getSession().getAttribute("adminSearch_holiday");
			
			if (obj == null) {
				page = this.holidayRepo.findAll(pageable);
				obj = new Holiday();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.holidayRepo.findAll(pageable);
				} else {
					page = this.holidayRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("adminSearch_holiday", obj);
			model.addAttribute("holiday", obj);
			
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
			
			request.getSession().setAttribute("listHolidayAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listHolidayAdmin_totalPages", totalPages);
			
			model.addAttribute("listHoliday", page.getContent());
			
			return "admin/holiday/list";
		
		} catch(Exception e) {
			return "redirect:/admin/holiday/list";
		}
	}
}


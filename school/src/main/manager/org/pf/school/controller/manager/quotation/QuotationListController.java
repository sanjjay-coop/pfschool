package org.pf.school.controller.manager.quotation;


import java.security.Principal;

import org.pf.school.controller.manager.ManagerBaseController;
import org.pf.school.model.Quotation;
import org.pf.school.repository.QuotationRepo;
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
@RequestMapping("/manager/quotation")
public class QuotationListController extends ManagerBaseController {
	
	@Autowired
	private QuotationRepo quotationRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listQuotation(@ModelAttribute Quotation quotation, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("managerSearch_quotation", quotation);
				
			return "redirect:/manager/quotation/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listQuotation(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "quote"));
			
			Page<Quotation> page;
			
			Quotation obj = (Quotation) request.getSession().getAttribute("managerSearch_quotation");
			
			if (obj == null) {
				page = this.quotationRepo.findAll(pageable);
				obj = new Quotation();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.quotationRepo.findAll(pageable);
				} else {
					page = this.quotationRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("managerSearch_quotation", obj);
			model.addAttribute("quotation", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listQuotation", page.getContent());
			
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
			
			request.getSession().setAttribute("listQuotationManager_pageNumber", pageNumber);
			request.getSession().setAttribute("listQuotationManager_totalPages", totalPages);
			
			return "manager/quotation/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listQuotation(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listQuotationManager_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listQuotationManager_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/manager/quotation/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "quote"));
			
			Page<Quotation> page;
			
			Quotation obj = (Quotation) request.getSession().getAttribute("managerSearch_quotation");
			
			if (obj == null) {
				page = this.quotationRepo.findAll(pageable);
				obj = new Quotation();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.quotationRepo.findAll(pageable);
				} else {
					page = this.quotationRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("managerSearch_quotation", obj);
			model.addAttribute("quotation", obj);
			
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
			
			request.getSession().setAttribute("listQuotationManager_pageNumber", pageNumber);
			request.getSession().setAttribute("listQuotationManager_totalPages", totalPages);
			
			model.addAttribute("listQuotation", page.getContent());
			
			return "manager/quotation/list";
		
		} catch(Exception e) {
			return "redirect:/manager/quotation/list";
		}
	}
}


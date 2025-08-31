package org.pf.school.controller.admin.contact;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Contact;
import org.pf.school.repository.ContactRepo;
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
@RequestMapping("/admin/contact")
public class ContactListController extends AdminBaseController {
	
	@Autowired
	private ContactRepo contactRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listContact(@ModelAttribute Contact contact, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("adminSearch_contact", contact);
				
			return "redirect:/admin/contact/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listContact(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "name"));
			
			Page<Contact> page;
			
			Contact obj = (Contact) request.getSession().getAttribute("adminSearch_contact");
			
			if (obj == null) {
				page = this.contactRepo.findAll(pageable);
				obj = new Contact();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.contactRepo.findAll(pageable);
				} else {
					page = this.contactRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("adminSearch_contact", obj);
			model.addAttribute("contact", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listContact", page.getContent());
			
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
			
			request.getSession().setAttribute("listContactAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listContactAdmin_totalPages", totalPages);
			
			return "admin/contact/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listContact(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listContactAdmin_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listContactAdmin_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/contact/list";
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
			
			Page<Contact> page;
			
			Contact obj = (Contact) request.getSession().getAttribute("adminSearch_contact");
			
			if (obj == null) {
				page = this.contactRepo.findAll(pageable);
				obj = new Contact();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.contactRepo.findAll(pageable);
				} else {
					page = this.contactRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("adminSearch_contact", obj);
			model.addAttribute("contact", obj);
			
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
			
			request.getSession().setAttribute("listContactAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listContactAdmin_totalPages", totalPages);
			
			model.addAttribute("listContact", page.getContent());
			
			return "admin/contact/list";
		
		} catch(Exception e) {
			return "redirect:/admin/contact/list";
		}
	}
}


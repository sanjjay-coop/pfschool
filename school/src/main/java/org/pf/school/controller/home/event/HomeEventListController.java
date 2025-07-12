package org.pf.school.controller.home.event;

import java.security.Principal;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.Event;
import org.pf.school.repository.EventRepo;
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
@RequestMapping("/home/event")
public class HomeEventListController extends HomeBaseController {
	
	@Autowired
	private EventRepo eventRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listEvent(@ModelAttribute Event event, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("homeSearch_event", event);
				
			return "redirect:/home/event/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listEvent(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.DESC, "fromDateTime"));
			
			Page<Event> page;
			
			Event obj = (Event) request.getSession().getAttribute("homeSearch_event");
			
			if (obj == null) {
				page = this.eventRepo.findAll(pageable);
				obj = new Event();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.eventRepo.findAll(pageable);
				} else {
					page = this.eventRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("homeSearch_event", obj);
			model.addAttribute("event", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listEvent", page.getContent());
			
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
			
			request.getSession().setAttribute("listEventHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listEventHome_totalPages", totalPages);
			
			return "home/event/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listEvent(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listEventHome_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listEventHome_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/home/event/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.DESC, "fromDateTime"));
			
			Page<Event> page;
			
			Event obj = (Event) request.getSession().getAttribute("homeSearch_event");
			
			if (obj == null) {
				page = this.eventRepo.findAll(pageable);
				obj = new Event();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.eventRepo.findAll(pageable);
				} else {
					page = this.eventRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("homeSearch_event", obj);
			model.addAttribute("event", obj);
			
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
			
			request.getSession().setAttribute("listEventHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listEventHome_totalPages", totalPages);
			
			model.addAttribute("listEvent", page.getContent());
			
			return "home/event/list";
		
		} catch(Exception e) {
			return "redirect:/home/event/list";
		}
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "event";
	}
}




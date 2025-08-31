package org.pf.school.controller.home.title;

import java.security.Principal;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.library.Title;
import org.pf.school.repository.library.TitleRepo;
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
@RequestMapping("/home/title/accNum")
public class HomeTitleListAccessionNumberController extends HomeBaseController {
	
	@Autowired
	private TitleRepo titleRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listTitle(@ModelAttribute Title title, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("homeSearch_title_accNum", title);
				
			return "redirect:/home/title/accNum/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listTitle(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "accessionNumber"));
			
			Page<Title> page;
			
			Title obj = (Title) request.getSession().getAttribute("homeSearch_title_accNum");
			
			if (obj == null) {
				page = this.titleRepo.findAll(pageable);
				obj = new Title();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.titleRepo.findAll(pageable);
				} else {
					page = this.titleRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("homeSearch_title_accNum", obj);
			model.addAttribute("title", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listTitle", page.getContent());
			
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
			
			request.getSession().setAttribute("listTitleHomeAccNum_pageNumber", pageNumber);
			request.getSession().setAttribute("listTitleHomeAccNum_totalPages", totalPages);
			
			return "home/title/listAccNum";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listTitle(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listTitleHomeAccNum_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listTitleHomeAccNum_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/home/title/accNum/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "accessionNumber"));
			
			Page<Title> page;
			
			Title obj = (Title) request.getSession().getAttribute("homeSearch_title_accNum");
			
			if (obj == null) {
				page = this.titleRepo.findAll(pageable);
				obj = new Title();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.titleRepo.findAll(pageable);
				} else {
					page = this.titleRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("homeSearch_title_accNum", obj);
			model.addAttribute("title", obj);
			
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
			
			request.getSession().setAttribute("listTitleHomeAccNum_pageNumber", pageNumber);
			request.getSession().setAttribute("listTitleHomeAccNum_totalPages", totalPages);
			
			model.addAttribute("listTitle", page.getContent());
			
			return "home/title/listAccNum";
		
		} catch(Exception e) {
			return "redirect:/home/title/accNum/list";
		}
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "title";
	}
}
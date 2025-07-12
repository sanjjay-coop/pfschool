package org.pf.school.controller.admin.news;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.News;
import org.pf.school.repository.NewsRepo;
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
@RequestMapping("/admin/news")
public class NewsListController extends AdminBaseController {
	
	@Autowired
	private NewsRepo newsRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listNews(@ModelAttribute News news, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("adminSearch_news", news);
				
			return "redirect:/admin/news/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listNews(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<News> page;
			
			News obj = (News) request.getSession().getAttribute("adminSearch_news");
			
			if (obj == null) {
				page = this.newsRepo.findAll(pageable);
				obj = new News();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.newsRepo.findAll(pageable);
				} else {
					page = this.newsRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("adminSearch_news", obj);
			model.addAttribute("news", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listNews", page.getContent());
			
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
			
			request.getSession().setAttribute("listNewsAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listNewsAdmin_totalPages", totalPages);
			
			return "admin/news/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listNews(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listNewsAdmin_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listNewsAdmin_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/news/list";
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
			
			Page<News> page;
			
			News obj = (News) request.getSession().getAttribute("adminSearch_news");
			
			if (obj == null) {
				page = this.newsRepo.findAll(pageable);
				obj = new News();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.newsRepo.findAll(pageable);
				} else {
					page = this.newsRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("adminSearch_news", obj);
			model.addAttribute("news", obj);
			
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
			
			request.getSession().setAttribute("listNewsAdmin_pageNumber", pageNumber);
			request.getSession().setAttribute("listNewsAdmin_totalPages", totalPages);
			
			model.addAttribute("listNews", page.getContent());
			
			return "admin/news/list";
		
		} catch(Exception e) {
			return "redirect:/admin/news/list";
		}
	}
}

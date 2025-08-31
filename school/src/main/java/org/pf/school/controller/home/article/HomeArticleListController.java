package org.pf.school.controller.home.article;

import java.security.Principal;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.Article;
import org.pf.school.repository.ArticleRepo;
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
@RequestMapping("/home/article")
public class HomeArticleListController extends HomeBaseController {
	
	@Autowired
	private ArticleRepo articleRepo;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listArticle(@ModelAttribute Article article, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("homeSearch_article", article);
				
			return "redirect:/home/article/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listArticle(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<Article> page;
			
			Article obj = (Article) request.getSession().getAttribute("homeSearch_article");
			
			if (obj == null) {
				page = this.articleRepo.findAll(pageable);
				obj = new Article();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.articleRepo.findAll(pageable);
				} else {
					page = this.articleRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("homeSearch_article", obj);
			model.addAttribute("article", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listArticle", page.getContent());
			
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
			
			request.getSession().setAttribute("listArticleHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listArticleHome_totalPages", totalPages);
			
			return "home/article/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listArticle(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listArticleHome_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listArticleHome_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/home/article/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<Article> page;
			
			Article obj = (Article) request.getSession().getAttribute("homeSearch_article");
			
			if (obj == null) {
				page = this.articleRepo.findAll(pageable);
				obj = new Article();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.articleRepo.findAll(pageable);
				} else {
					page = this.articleRepo.findBySearchStringContainingIgnoreCase(obj.getSearchFor(), pageable);
				}
			}
			
			totalPages = page.getTotalPages();
			
			request.getSession().setAttribute("homeSearch_article", obj);
			model.addAttribute("article", obj);
			
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
			
			request.getSession().setAttribute("listArticleHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listArticleHome_totalPages", totalPages);
			
			model.addAttribute("listArticle", page.getContent());
			
			return "home/article/list";
		
		} catch(Exception e) {
			return "redirect:/home/article/list";
		}
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "article";
	}
}

package org.pf.school.controller.admin.news;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.News;
import org.pf.school.service.NewsService;
import org.pf.school.validator.NewsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/news/addNew")
public class NewsAddController extends AdminBaseController {

	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsValidator newsValidator;
	
	@GetMapping
	public String newsAdd(Model model) {
		
		News news = new News();
		
		model.addAttribute(news);
		
		return "admin/news/addNew";
	}
	
	@PostMapping
	public String newsAdd(@ModelAttribute News news,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.newsValidator.validate(news, result);
		
		if (result.hasErrors()) {
			return "admin/news/addNew";
		}
		
		try {
			TransactionResult tr = this.newsService.addNews(news, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/news/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/news/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/news/addNew";
		}
	}
}
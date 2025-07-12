package org.pf.school.controller.admin.article;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Article;
import org.pf.school.service.ArticleService;
import org.pf.school.validator.ArticleValidator;
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
@RequestMapping(value = "/admin/article/addNew")
public class ArticleAddController extends AdminBaseController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleValidator articleValidator;
	
	@GetMapping
	public String articleAdd(Model model) {
		
		Article article = new Article();
		
		model.addAttribute(article);
		
		return "admin/article/addNew";
	}
	
	@PostMapping
	public String articleAdd(@ModelAttribute Article article,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.articleValidator.validate(article, result);
		
		if (result.hasErrors()) {
			return "admin/article/addNew";
		}
		
		try {
			TransactionResult tr = this.articleService.addArticle(article, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/article/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/article/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/article/addNew";
		}
	}
}

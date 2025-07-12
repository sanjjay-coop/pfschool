package org.pf.school.controller.home.article;

import java.util.UUID;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.Article;
import org.pf.school.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/home/article/view")
public class HomeArticleViewController extends HomeBaseController {

	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/{id}")
	public String homeViewArticle(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Article article = (Article) this.articleService.getById(id);
			
			if (article == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/";
			}
	
			model.addAttribute("article", article);
			
			return "home/article/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/";
		}
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "about";
	}
	
}

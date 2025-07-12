package org.pf.school.controller.admin.article;

import java.security.Principal;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/article/edit")
public class ArticleEditController extends AdminBaseController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleValidator articleValidator;
	
	@GetMapping("/{id}")
	public String editArticle(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Article article = (Article) this.articleService.getById(id);
			
			if (article == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/article/addNew";
			}
	
			model.addAttribute("article", article);
			
			return "admin/article/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/article/addNew";
		}
	}

	@PostMapping("/*")
	public String editArticle(@ModelAttribute Article article,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.articleValidator.validate(article, result);
		
		if (result.hasErrors()) {
			return "admin/article/edit";
		}
		
		try {
			TransactionResult tr = this.articleService.updateArticle(article, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/article/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/article/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/article/edit/"+article.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/article/edit/"+article.getId();
		}
	}
}


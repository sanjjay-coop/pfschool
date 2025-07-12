package org.pf.school.controller.admin.news;

import java.security.Principal;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/news/edit")
public class NewsEditController extends AdminBaseController {

	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsValidator newsValidator;
	
	@GetMapping("/{id}")
	public String editNews(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			News news = (News) this.newsService.getById(id);
			
			if (news == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/news/addNew";
			}
	
			model.addAttribute("news", news);
			
			return "admin/news/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/news/addNew";
		}
	}

	@PostMapping("/*")
	public String editNews(@ModelAttribute News news,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.newsValidator.validate(news, result);
		
		if (result.hasErrors()) {
			return "admin/news/edit";
		}
		
		try {
			TransactionResult tr = this.newsService.updateNews(news, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/news/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/news/view/"+news.getId();
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/news/edit/"+news.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/news/edit/"+news.getId();
		}
	}
}

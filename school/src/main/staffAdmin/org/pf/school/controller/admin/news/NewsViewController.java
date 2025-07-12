package org.pf.school.controller.admin.news;

import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.News;
import org.pf.school.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/news/view")
public class NewsViewController extends AdminBaseController {

	@Autowired
	private NewsService newsService;
	
	@GetMapping("/{id}")
	public String editNews(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			News news = (News) this.newsService.getById(id);
			
			if (news == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/news/list/current";
			}
	
			model.addAttribute("news", news);
			
			return "admin/news/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/news/list/current";
		}
	}
}

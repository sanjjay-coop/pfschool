package org.pf.school.controller.home.title;

import java.util.UUID;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.library.Title;
import org.pf.school.service.library.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/home/title/accNum/view")
public class HomeTitleAccNumViewController extends HomeBaseController {

	@Autowired
	private TitleService titleService;
	
	@GetMapping("/{id}")
	public String homeViewTitle(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Title title = (Title) this.titleService.getById(id);
			
			if (title == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/";
			}
	
			model.addAttribute("title", title);
			
			return "home/title/viewAccNum";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/";
		}
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "title";
	}
	
}

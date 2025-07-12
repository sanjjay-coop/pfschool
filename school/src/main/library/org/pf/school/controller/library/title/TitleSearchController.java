package org.pf.school.controller.library.title;

import java.security.Principal;
import java.util.List;

import org.pf.school.controller.library.LibraryBaseController;
import org.pf.school.forms.SimpleSearchForm;
import org.pf.school.model.library.Title;
import org.pf.school.repository.library.TitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TitleSearchController extends LibraryBaseController {
	
	@Autowired
	private TitleRepo titleRepo;
	
	@GetMapping("/library/title/searchResult")
	public String titleSearchList(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		SimpleSearchForm ssf = (SimpleSearchForm) request.getSession().getAttribute("searchTitleForm");
		
		if (ssf == null) {
			reat.addFlashAttribute("message", "No search options are avaialable.");
			return "redirect:/library";
		}
		
		List<Title> listTitle = this.titleRepo.listTitle(ssf.getSearchString().toLowerCase());
		
		if (listTitle.isEmpty()) {
			reat.addFlashAttribute("message", "0 results found.");
			return "redirect:/library";
		}
		
		model.addAttribute("listTitle", listTitle);
		
		return "library/title/searchResult";
		
	}
}

package org.pf.school.controller.library.library;

import java.security.Principal;

import org.pf.school.controller.library.LibraryBaseController;
import org.pf.school.model.library.Library;
import org.pf.school.repository.library.LibraryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/library/library")
public class LibraryListController extends LibraryBaseController {
	
	@Autowired
	private LibraryRepo libraryRepo;
	
	@GetMapping("/list")
	public String listLibrary(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
		
		Page<Library> page = this.libraryRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listLibrary", page.getContent());
		
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
		
		request.getSession().setAttribute("listLibrary_pageNumber", pageNumber);
		request.getSession().setAttribute("listLibrary_totalPages", totalPages);
		
		return "library/library/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listLibrary(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listLibrary_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listLibrary_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/library/library/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<Library> page = this.libraryRepo.findAll(pageable);
			
			totalPages = page.getTotalPages();
			
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
			
			request.getSession().setAttribute("listLibrary_pageNumber", pageNumber);
			request.getSession().setAttribute("listLibrary_totalPages", totalPages);
			
			model.addAttribute("listLibrary", page.getContent());
			
			return "library/library/list";
		
		} catch(Exception e) {
			return "redirect:/library/library/list";
		}
	}
}

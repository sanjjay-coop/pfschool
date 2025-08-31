package org.pf.school.controller.library.titleType;

import java.security.Principal;

import org.pf.school.controller.library.LibraryBaseController;
import org.pf.school.model.library.TitleType;
import org.pf.school.repository.library.TitleTypeRepo;
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
@RequestMapping("/library/titleType")
public class TitleTypeListController extends LibraryBaseController {
	
	@Autowired
	private TitleTypeRepo titleTypeRepo;
	
	@GetMapping("/list")
	public String listTitleType(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "id"));
		
		Page<TitleType> page = this.titleTypeRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listTitleType", page.getContent());
		
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
		
		request.getSession().setAttribute("listTitleType_pageNumber", pageNumber);
		request.getSession().setAttribute("listTitleType_totalPages", totalPages);
		
		return "library/titleType/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listTitleType(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listTitleType_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listTitleType_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/library/titleType/list";
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
			
			Page<TitleType> page = this.titleTypeRepo.findAll(pageable);
			
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
			
			request.getSession().setAttribute("listTitleType_pageNumber", pageNumber);
			request.getSession().setAttribute("listTitleType_totalPages", totalPages);
			
			model.addAttribute("listTitleType", page.getContent());
			
			return "library/titleType/list";
		
		} catch(Exception e) {
			return "redirect:/library/titleType/list";
		}
	}
}
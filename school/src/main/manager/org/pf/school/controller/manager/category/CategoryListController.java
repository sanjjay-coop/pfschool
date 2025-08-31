package org.pf.school.controller.manager.category;

import java.security.Principal;

import org.pf.school.controller.manager.ManagerBaseController;
import org.pf.school.model.Category;
import org.pf.school.repository.CategoryRepo;
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
@RequestMapping("/manager/category")
public class CategoryListController extends ManagerBaseController {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@GetMapping("/list")
	public String listCategory(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "code"));
		
		Page<Category> page = this.categoryRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listCategory", page.getContent());
		
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
		
		request.getSession().setAttribute("listCategory_pageNumber", pageNumber);
		request.getSession().setAttribute("listCategory_totalPages", totalPages);
		
		return "manager/category/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listCategory(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listCategory_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listCategory_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/manager/category/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, "code"));
			
			Page<Category> page = this.categoryRepo.findAll(pageable);
			
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
			
			request.getSession().setAttribute("listCategory_pageNumber", pageNumber);
			request.getSession().setAttribute("listCategory_totalPages", totalPages);
			
			model.addAttribute("listCategory", page.getContent());
			
			return "manager/category/list";
		
		} catch(Exception e) {
			return "redirect:/manager/category/list";
		}
	}
}

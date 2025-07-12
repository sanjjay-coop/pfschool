package org.pf.school.controller.manager.category;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.manager.ManagerBaseController;
import org.pf.school.model.Category;
import org.pf.school.service.CategoryService;
import org.pf.school.validator.edit.CategoryEditValidator;
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
@RequestMapping("/manager/category/edit")
public class CategoryEditController extends ManagerBaseController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryEditValidator categoryEditValidator;
	
	@GetMapping("/{id}")
	public String editCategory(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Category category = (Category) this.categoryService.getById(id);
			
			if (category == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/category/addNew";
			}
	
			model.addAttribute("category", category);
			
			return "manager/category/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/category/addNew";
		}
	}

	@PostMapping("/*")
	public String editCategory(@ModelAttribute Category category,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.categoryEditValidator.validate(category, result);
		
		if (result.hasErrors()) {
			return "manager/category/edit";
		}
		
		try {
			TransactionResult tr = this.categoryService.updateCategory(category, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/category/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/category/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/category/edit/"+category.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/category/edit/"+category.getId();
		}
	}
}

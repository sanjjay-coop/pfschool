package org.pf.school.controller.admin.gallery;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Gallery;
import org.pf.school.service.GalleryService;
import org.pf.school.validator.GalleryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/gallery/addNew")
public class GalleryAddController extends AdminBaseController {

	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private GalleryValidator galleryValidator;
	
	@GetMapping
	public String galleryAdd(Model model) {
		
		Gallery gallery = new Gallery();
		
		model.addAttribute(gallery);
		
		return "admin/gallery/addNew";
	}
	
	@PostMapping
	public String galleryAdd(@ModelAttribute Gallery gallery,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.galleryValidator.validate(gallery, result);
		
		if (result.hasErrors()) {
			return "admin/gallery/addNew";
		}
		
		try {
			TransactionResult tr = this.galleryService.addGallery(gallery, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/gallery/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/gallery/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/gallery/addNew";
		}
	}
}

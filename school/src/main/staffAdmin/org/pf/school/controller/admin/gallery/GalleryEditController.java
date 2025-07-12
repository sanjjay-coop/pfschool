package org.pf.school.controller.admin.gallery;

import java.security.Principal;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/gallery/edit")
public class GalleryEditController extends AdminBaseController {

	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private GalleryValidator galleryValidator;
	
	@GetMapping("/{id}")
	public String editGallery(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Gallery gallery = (Gallery) this.galleryService.getById(id);
			
			if (gallery == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/gallery/addNew";
			}
	
			model.addAttribute("gallery", gallery);
			
			return "admin/gallery/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/gallery/addNew";
		}
	}

	@PostMapping("/*")
	public String editGallery(@ModelAttribute Gallery gallery,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.galleryValidator.validate(gallery, result);
		
		if (result.hasErrors()) {
			return "admin/gallery/edit";
		}
		
		try {
			TransactionResult tr = this.galleryService.updateGallery(gallery, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/gallery/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/gallery/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/gallery/edit/"+gallery.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/gallery/edit/"+gallery.getId();
		}
	}
}

package org.pf.school.controller.home.gallery;

import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Gallery;
import org.pf.school.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/gallery/view")
public class HomeGalleryViewController extends AdminBaseController {
	
	@Autowired
	private GalleryService galleryService;
	
	@GetMapping("/{id}")
	public String viewGallery(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Gallery gallery = (Gallery) this.galleryService.getById(id);
			
			if (gallery == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/home/gallery/list/current";
			}

			model.addAttribute("gallery", gallery);
			
			return "home/gallery/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/home/gallery/list/current";
		}
	}
}

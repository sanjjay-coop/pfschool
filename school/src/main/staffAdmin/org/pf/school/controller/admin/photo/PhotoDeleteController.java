package org.pf.school.controller.admin.photo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Photo;
import org.pf.school.service.PhotoService;
import org.pf.school.service.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/photo/delete")
public class PhotoDeleteController extends AdminBaseController {

	@Autowired
	private PhotoService photoService;
	
	@Autowired
	FileStorageService storageService;
	
	@GetMapping("/{id}")
	public String deletePhoto(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {

		Photo photo = (Photo) this.photoService.getById(id);
		
		if (photo == null) {
			reat.addFlashAttribute("message", "File not found.");
			return "redirect:/manager/gallery/listAll";
		}
		
		UUID galleryId = photo.getGallery().getId();
		
		this.photoService.deletePhoto(id, principal.getName());
		
		try {
			
			String mediaLocation = this.getParameters().getDataDirectory() + 
					"/gallery/" + photo.getGallery().getId();
			
			Path root = Paths.get(mediaLocation + "/" + photo.getFileName());
			
			if (storageService.deleteAll(root)) {
				
				this.photoService.deletePhoto(id, principal.getName());
				
				reat.addFlashAttribute("message", "File <" + photo.getFileName() + "> deleted successfully.");
				return "redirect:/admin/gallery/view/" + galleryId;
			} else {
				reat.addFlashAttribute("message", "File <" + photo.getFileName() + "> not deleted.");
				return "redirect:/admin/gallery/view/" + galleryId;
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/admin/gallery/view/" + galleryId;
		}
	}

}


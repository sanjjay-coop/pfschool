package org.pf.school.controller.admin.gallery;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Gallery;
import org.pf.school.model.Photo;
import org.pf.school.service.GalleryService;
import org.pf.school.service.PhotoService;
import org.pf.school.service.storage.FileStorageService;
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
@RequestMapping("/admin/gallery/view")
public class GalleryViewController extends AdminBaseController {
	
	public static final String PNG_MIME_TYPE="image/png";
	public static final String JPEG_MIME_TYPE="image/jpeg";
	public static final String JPG_MIME_TYPE="image/jpeg";
	public static final long SIZE_IN_BYTES = 1097152;

	@Autowired
	private GalleryService galleryService;
	
	@Autowired 
	private PhotoService photoService;
	
	@Autowired
	FileStorageService fileStorageService;
	
	@GetMapping("/{id}")
	public String editGallery(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Gallery gallery = (Gallery) this.galleryService.getById(id);
			
			if (gallery == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/gallery/list/current";
			}
	
			Photo photo = new Photo();
			photo.setGallery(gallery);
			
			model.addAttribute("photo", photo);
			model.addAttribute("gallery", gallery);
			
			return "admin/gallery/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/gallery/list/current";
		}
	}
	
	@PostMapping("/*")
	public String addPhotos(@ModelAttribute Photo photo,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		if (photo.getGallery()==null) {
			reat.addFlashAttribute("message", "No record found for gallery.");
			return "redirect:/admin/gallery/list/current";
		}
		
		String mediaLocation = this.getParameters().getDataDirectory() 
				+ "/gallery/"
				+ photo.getGallery().getId();
		
		System.out.println(mediaLocation);
		
		List<String> messages = new ArrayList<>();
		
		Path root = Paths.get(mediaLocation);
		
		fileStorageService.init(root);
		
		Arrays.asList(photo.getFiles()).stream().forEach(file -> {
			if (!(PNG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) || JPG_MIME_TYPE.equalsIgnoreCase(file.getContentType()) || JPEG_MIME_TYPE.equalsIgnoreCase(file.getContentType()))){
	            messages.add(file.getOriginalFilename() + " <Failed> - " + "Invalid file type .");
	        } else if (file.getSize() > SIZE_IN_BYTES){
	        	messages.add(file.getOriginalFilename() + " <Failed> - " + "Invalid file size.");
	        } else {
				try {
					fileStorageService.save(file, root);
					messages.add(file.getOriginalFilename() + " [Successful]");
					
					Photo obj = new Photo();
					
					obj.setGallery(photo.getGallery());
					obj.setFileName(file.getOriginalFilename());
					
					photoService.addPhoto(obj, principal.getName());
					
				} catch (Exception e) {
					messages.add(file.getOriginalFilename() + " <Failed> - " + e.getMessage());
				}
	        }
		});

		reat.addFlashAttribute("message", messages);

		return "redirect:/admin/gallery/view/" + photo.getGallery().getId();
	}
}


package org.pf.school.controller.home.download;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.model.Carousel;
import org.pf.school.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CarouselPhotoDownloadController {

	@Autowired
	private CarouselService carouselService;

	@GetMapping("/home/carousel/download/{id}")
	public ResponseEntity<byte[]> getCarousel(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {
	    
		Carousel carousel = (Carousel) this.carouselService.getById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + carousel.getFileName() + "\"")
	        .body(carousel.getFileData());
	} 
}

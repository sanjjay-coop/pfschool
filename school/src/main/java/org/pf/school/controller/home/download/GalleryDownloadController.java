package org.pf.school.controller.home.download;

import java.util.UUID;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.Gallery;
import org.pf.school.service.GalleryService;
import org.pf.school.service.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/gallery/download")
public class GalleryDownloadController  extends HomeBaseController {

	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	FileStorageService storageService;
	
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable UUID id) {
	    
		Gallery gallery = (Gallery) galleryService.getById(id);
		
		return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + gallery.getFileName() + "\"")
		        .body(gallery.getFileData());
	}

}

package org.pf.school.controller.home.download;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.Photo;
import org.pf.school.service.PhotoService;
import org.pf.school.service.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/gallery/photo/download")
public class GalleryPhotoDownloadController extends HomeBaseController {

	@Autowired
	private PhotoService photoService;
	
	@Autowired
	FileStorageService storageService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Resource> getFile(@PathVariable UUID id) {
	    
		Photo photo = (Photo) photoService.getById(id);
		
		String mediaLocation = this.getParameters().getDataDirectory() + 
				"/gallery/" + photo.getGallery().getId();
		
		Path root = Paths.get(mediaLocation);
		
		Resource file = storageService.load(photo.getFileName(), root);
		
		return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

}

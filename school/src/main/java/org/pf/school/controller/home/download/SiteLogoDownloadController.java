package org.pf.school.controller.home.download;

import org.pf.school.model.SiteLogo;
import org.pf.school.repository.SiteLogoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteLogoDownloadController {

	@Autowired
	private SiteLogoRepo siteLogoRepo;

	@GetMapping("/siteLogo")
	public ResponseEntity<byte[]> getSiteLogo() {
	    
		SiteLogo siteLogo = this.siteLogoRepo.getSiteLogo();

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + siteLogo.getFileName() + "\"")
	        .body(siteLogo.getFileData());
	} 
}

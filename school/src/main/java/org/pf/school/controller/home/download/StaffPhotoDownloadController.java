package org.pf.school.controller.home.download;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.model.Staff;
import org.pf.school.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StaffPhotoDownloadController {

	@Autowired
	private StaffService staffService;

	@GetMapping("/home/staff/photo/download/{id}")
	public ResponseEntity<byte[]> getPhoto(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {
	    
		Staff obj = (Staff) this.staffService.getById(id);

		//System.out.println(id);
		
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + obj.getFileName() + "\"")
	        .body(obj.getFileData());
	} 
}

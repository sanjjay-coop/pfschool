package org.pf.school.controller.home.download;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.model.Leader;
import org.pf.school.service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LeaderPhotoDownloadController {

	@Autowired
	private LeaderService leaderService;

	@GetMapping("/home/leader/photo/download/{id}")
	public ResponseEntity<byte[]> getLeader(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {
	    
		Leader leader = (Leader) this.leaderService.getById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + leader.getFileName() + "\"")
	        .body(leader.getFileData());
	} 
}

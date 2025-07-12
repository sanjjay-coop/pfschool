package org.pf.school.controller.home.contact;

import java.util.List;
import java.util.UUID;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.Contact;
import org.pf.school.repository.ContactRepo;
import org.pf.school.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeContactListController extends HomeBaseController {

	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ContactRepo contactRepo;
	
	@ModelAttribute("listContact")
	public List<Contact> getListContact(){
		return this.contactRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping("/home/contacts")
	public String contactsView() {
		
		return "home/contact/contacts";
	}
	
	@GetMapping("/home/contact/photo/{id}")
	public ResponseEntity<byte[]> getSiteLogo(@PathVariable UUID id) {
	    
		Contact contact = (Contact) this.contactService.getById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + contact.getFileName() + "\"")
	        .body(contact.getFileData());
	} 
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "contacts";
	}
}

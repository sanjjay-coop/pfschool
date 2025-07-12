package org.pf.school.controller.admin.contact;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Contact;
import org.pf.school.service.ContactService;
import org.pf.school.validator.ContactValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/contact/addNew")
public class ContactAddController extends AdminBaseController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ContactValidator contactValidator;
	
	@GetMapping
	public String contactAdd(Model model) {
		
		Contact contact = new Contact();
		
		model.addAttribute(contact);
		
		return "admin/contact/addNew";
	}
	
	@PostMapping
	public String contactAdd(@ModelAttribute Contact contact,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.contactValidator.validate(contact, result);
		
		if (result.hasErrors()) {
			return "admin/contact/addNew";
		}
		
		try {
			TransactionResult tr = this.contactService.addContact(contact, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/contact/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/contact/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/contact/addNew";
		}
	}
}

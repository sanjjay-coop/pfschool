package org.pf.school.controller.admin.contact;

import java.security.Principal;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/contact/edit")
public class ContactEditController extends AdminBaseController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ContactValidator contactValidator;
	
	@GetMapping("/{id}")
	public String editContact(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Contact contact = (Contact) this.contactService.getById(id);
			
			if (contact == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/contact/addNew";
			}
	
			model.addAttribute("contact", contact);
			
			return "admin/contact/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/contact/addNew";
		}
	}

	@PostMapping("/*")
	public String editContact(@ModelAttribute Contact contact,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.contactValidator.validate(contact, result);
		
		if (result.hasErrors()) {
			return "admin/contact/edit";
		}
		
		try {
			TransactionResult tr = this.contactService.updateContact(contact, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/contact/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/contact/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/contact/edit/"+contact.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/contact/edit/"+contact.getId();
		}
	}
}

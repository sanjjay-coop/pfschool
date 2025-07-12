package org.pf.school.controller.admin.contact;

import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Contact;
import org.pf.school.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/contact/view")
public class ContactViewController extends AdminBaseController {

	@Autowired
	private ContactService contactService;
	
	@GetMapping("/{id}")
	public String editContact(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Contact contact = (Contact) this.contactService.getById(id);
			
			if (contact == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/contact/list/current";
			}
	
			model.addAttribute("contact", contact);
			
			return "admin/contact/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/contact/list/current";
		}
	}
}

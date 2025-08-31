package org.pf.school.controller.admin.schoolMessage;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.SchoolMessage;
import org.pf.school.service.SchoolMessageService;
import org.pf.school.validator.SchoolMessageValidator;
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
@RequestMapping(value = "/admin/schoolMessage")
public class AdminSendMessageController extends AdminBaseController {

	@Autowired
	private SchoolMessageService schoolMessageService;
	
	@Autowired
	private SchoolMessageValidator schoolMessageValidator;
	
	@GetMapping
	public String schoolMessageAdd(Model model) {
		
		SchoolMessage schoolMessage = new SchoolMessage();
		
		model.addAttribute(schoolMessage);
		
		return "admin/schoolMessage/addNew";
	}
	
	@PostMapping
	public String schoolMessageAdd(@ModelAttribute SchoolMessage schoolMessage,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.schoolMessageValidator.validate(schoolMessage, result);
		
		if (result.hasErrors()) {
			return "admin/schoolMessage/addNew";
		}
		
		try {
			TransactionResult tr = this.schoolMessageService.sendMessage(schoolMessage, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Messages could not be sent.");
				return "redirect:/admin/schoolMessage";
			} else {
				reat.addFlashAttribute("message", "Messages sent successfully.");
			}
			
			return "redirect:/admin/schoolMessage";
		} catch (Exception e) {
			reat.addFlashAttribute("schoolMessage", e.getMessage());
			return "redirect:/admin/schoolMessage";
		}
	}
}

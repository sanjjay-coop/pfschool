package org.pf.school.controller.manager.advert;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.manager.ManagerBaseController;
import org.pf.school.model.Advert;
import org.pf.school.service.AdvertService;
import org.pf.school.validator.AdvertValidator;
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
@RequestMapping("/manager/advert/update")
public class AdvertEditController extends ManagerBaseController {

	@Autowired
	private AdvertService advertService;
	
	@Autowired
	private AdvertValidator advertValidator;
		
	@GetMapping("/{id}")
	public String editAdvert(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Advert advert = (Advert) this.advertService.getById(id);
			
			if (advert == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/advert/addNew";
			}
	
			model.addAttribute("advert", advert);
			
			return "manager/advert/update";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/advert/addNew";
		}
	}

	@PostMapping("/*")
	public String editAdvert(@ModelAttribute Advert advert,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.advertValidator.validate(advert, result);
		
		if (result.hasErrors()) {
			return "manager/advert/update";
		}
		
		try {
			TransactionResult tr = this.advertService.updateAdvert(advert, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/advert/addNew";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/advert/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/advert/update/"+advert.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/advert/update/"+advert.getId();
		}
	}
}

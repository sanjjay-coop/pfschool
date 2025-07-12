package org.pf.school.controller.manager.carousel;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.manager.ManagerBaseController;
import org.pf.school.model.Carousel;
import org.pf.school.service.CarouselService;
import org.pf.school.validator.edit.CarouselEditValidator;
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
@RequestMapping("/manager/carousel/edit")
public class CarouselEditController extends ManagerBaseController {

	@Autowired
	private CarouselService carouselService;
	
	@Autowired
	private CarouselEditValidator carouselEditValidator;
	
	@GetMapping("/{id}")
	public String editCarousel(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Carousel carousel = (Carousel) this.carouselService.getById(id);
			
			if (carousel == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/carousel/addNew";
			}
	
			model.addAttribute("carousel", carousel);
			
			return "manager/carousel/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/carousel/addNew";
		}
	}

	@PostMapping("/*")
	public String editCarousel(@ModelAttribute Carousel carousel,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.carouselEditValidator.validate(carousel, result);
		
		if (result.hasErrors()) {
			return "manager/carousel/edit";
		}
		
		try {
			TransactionResult tr = this.carouselService.updateCarousel(carousel, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/carousel/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/carousel/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/carousel/edit/"+carousel.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/carousel/edit/"+carousel.getId();
		}
	}
}


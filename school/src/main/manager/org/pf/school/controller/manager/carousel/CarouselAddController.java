package org.pf.school.controller.manager.carousel;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.manager.ManagerBaseController;
import org.pf.school.model.Carousel;
import org.pf.school.service.CarouselService;
import org.pf.school.validator.CarouselValidator;
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
@RequestMapping(value = "/manager/carousel/addNew")
public class CarouselAddController extends ManagerBaseController {

	@Autowired
	private CarouselService carouselService;
	
	@Autowired
	private CarouselValidator carouselValidator;
	
	@GetMapping
	public String carouselAdd(Model model) {
		
		Carousel carousel = new Carousel();
		
		model.addAttribute(carousel);
		
		return "manager/carousel/addNew";
	}
	
	@PostMapping
	public String carouselAdd(@ModelAttribute Carousel carousel,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.carouselValidator.validate(carousel, result);
		
		if (result.hasErrors()) {
			return "manager/carousel/addNew";
		}
		
		try {
			TransactionResult tr = this.carouselService.addCarousel(carousel, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "manager/carousel/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/manager/carousel/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "manager/carousel/addNew";
		}
	}
}

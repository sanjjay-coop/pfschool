package org.pf.school.controller.admin.leader;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Leader;
import org.pf.school.service.LeaderService;
import org.pf.school.validator.add.LeaderAddValidator;
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
@RequestMapping(value = "/admin/leader/addNew")
public class LeaderAddController extends AdminBaseController {

	@Autowired
	private LeaderService leaderService;
	
	@Autowired
	private LeaderAddValidator leaderAddValidator;
	
	@GetMapping
	public String leaderAdd(Model model) {
		
		Leader leader = new Leader();
		
		model.addAttribute(leader);
		
		return "admin/leader/addNew";
	}
	
	@PostMapping
	public String leaderAdd(@ModelAttribute Leader leader,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.leaderAddValidator.validate(leader, result);
		
		if (result.hasErrors()) {
			return "admin/leader/addNew";
		}
		
		try {
			TransactionResult tr = this.leaderService.addLeader(leader, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/leader/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
				return "redirect:/admin/leader/list/current";
			}
			
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/leader/addNew";
		}
	}
}

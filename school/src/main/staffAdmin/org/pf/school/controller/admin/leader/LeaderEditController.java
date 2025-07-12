package org.pf.school.controller.admin.leader;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Leader;
import org.pf.school.service.LeaderService;
import org.pf.school.validator.edit.LeaderEditValidator;
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
@RequestMapping("/admin/leader/edit")
public class LeaderEditController extends AdminBaseController {

	@Autowired
	private LeaderService leaderService;
	
	@Autowired
	private LeaderEditValidator leaderEditValidator;
	
	@GetMapping("/{id}")
	public String editLeader(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Leader leader = (Leader) this.leaderService.getById(id);
			
			if (leader == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/leader/addNew";
			}
	
			model.addAttribute("leader", leader);
			
			return "admin/leader/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/leader/addNew";
		}
	}

	@PostMapping("/*")
	public String editLeader(@ModelAttribute Leader leader,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.leaderEditValidator.validate(leader, result);
		
		if (result.hasErrors()) {
			return "admin/leader/edit";
		}
		
		try {
			TransactionResult tr = this.leaderService.updateLeader(leader, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/leader/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/leader/view/"+leader.getId();
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/leader/edit/"+leader.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/leader/edit/"+leader.getId();
		}
	}
}
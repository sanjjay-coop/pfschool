package org.pf.school.controller.admin.leader;

import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Leader;
import org.pf.school.service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/leader/view")
public class LeaderViewController extends AdminBaseController {

	@Autowired
	private LeaderService leaderService;
	
	@GetMapping("/{id}")
	public String editLeader(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Leader leader = (Leader) this.leaderService.getById(id);
			
			if (leader == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/leader/list/current";
			}
	
			model.addAttribute("leader", leader);
			
			return "admin/leader/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/leader/list/current";
		}
	}
}


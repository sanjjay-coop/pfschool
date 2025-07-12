package org.pf.school.controller.admin.assessment;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/assessment/delete")
public class AssessmentDeleteController extends AdminBaseController {

	@Autowired
	private AssessmentService assessmentService;

	@GetMapping("/{id}")
	public String deleteAssessment(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			
			TransactionResult tr = this.assessmentService.deleteAssessment(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				
			}
			return "redirect:/admin/assessment/list/current";
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/admin/assessment/list/current";
		}
		
	}
}

package org.pf.school.controller.admin.sessionDetailStudent;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.service.SessionDetailStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/sessionDetailStudent/delete")
public class SessionDetailStudentDeleteController extends AdminBaseController {

	@Autowired
	private SessionDetailStudentService sessionDetailStudentService;

	@GetMapping("/{id}")
	public String deleteSessionDetailStudent(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {

		SessionDetailStudent obj = (SessionDetailStudent) this.sessionDetailStudentService.getById(id);
		
		if (obj == null) {
			reat.addFlashAttribute("message", "No such record is found.");
			return "redirect:/admin/sessionDetail/list/current";
		}
		
		UUID sessionDetailId = obj.getSessionDetail().getId();
		
		try {
			
			TransactionResult tr = this.sessionDetailStudentService.deleteSessionDetailStudent(id, principal.getName());
			
			if (tr == null ) {
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/admin/sessionDetail/view/" + sessionDetailId;
			} else if (tr.isStatus()){
				reat.addFlashAttribute("message", "Record deleted successfully.");
				return "redirect:/admin/sessionDetail/view/" + sessionDetailId;
			} else {
				reat.addFlashAttribute("message", "Record could not be deleted.");
				return "redirect:/admin/sessionDetail/view/" + sessionDetailId;
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/admin/sessionDetail/view/" + sessionDetailId;
		}
	}
}

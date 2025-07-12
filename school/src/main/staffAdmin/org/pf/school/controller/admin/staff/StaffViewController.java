package org.pf.school.controller.admin.staff;

import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Staff;
import org.pf.school.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/staff/view")
public class StaffViewController extends AdminBaseController {

	@Autowired
	private StaffService staffService;
	
	@GetMapping("/{id}")
	public String editStaff(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Staff staff = (Staff) this.staffService.getById(id);
			
			if (staff == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/staff/list/current";
			}
	
			model.addAttribute("staff", staff);
			
			return "admin/staff/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/staff/list/current";
		}
	}
}

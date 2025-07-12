package org.pf.school.controller.admin.staff;

import java.security.Principal;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
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
@RequestMapping("/admin/staff/delete")
public class StaffDeleteController extends AdminBaseController {

	@Autowired
	private StaffService staffService;

	@GetMapping("/{id}")
	public String deleteStaff(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {

		Staff staff = (Staff) this.staffService.getById(id);
		
		if (staff == null) {
			
			reat.addFlashAttribute("message", "No such a staff record is found.");
			return "redirect:/admin/staff/list/current";
			
		} else {
			if (staff.getMember().getUid().equals(principal.getName())) {
				reat.addFlashAttribute("message", "Error: Cannot delete self.");
				return "redirect:/admin/staff/view/" + id;
			}
		}
		
		try {
			
			TransactionResult tr = this.staffService.deleteStaff(id, principal.getName());
			
			if (tr == null ) {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				
			} else if (tr.isStatus()){
				
				reat.addFlashAttribute("message", "Record deleted successfully.");
				
			} else {
				
				reat.addFlashAttribute("message", "Record could not be deleted.");
				
			}
			
			return "redirect:/admin/staff/list/current";
			
		} catch (Exception e) {
			
			reat.addFlashAttribute("message", "Error: " + e.getMessage());
			return "redirect:/admin/staff/view/" + id;
		}
		
	}
}


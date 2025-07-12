package org.pf.school.controller.admin.staff;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Category;
import org.pf.school.model.Gender;
import org.pf.school.model.Role;
import org.pf.school.model.Staff;
import org.pf.school.repository.CategoryRepo;
import org.pf.school.repository.GenderRepo;
import org.pf.school.repository.RoleRepo;
import org.pf.school.service.StaffService;
import org.pf.school.validator.edit.StaffEditValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/admin/staff/edit")
public class StaffEditController extends AdminBaseController {

	@Autowired
	private StaffService staffService;
	
	@Autowired
	private StaffEditValidator staffEditValidator;

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private GenderRepo genderRepo;
	
	@ModelAttribute("listCategory")
	public List<Category> getListCategory(){
		return this.categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listGender")
	public List<Gender> getListGender(){
		return this.genderRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Autowired
	private RoleRepo roleRepo;
	
	@ModelAttribute("listRole")
	public List<Role> getListRole(){
		return this.roleRepo.findAll(Sort.by(Sort.Direction.ASC, "code"));
	}
	
	
	@GetMapping("/{id}")
	public String editStaff(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {

		try {
			Staff staff = (Staff) this.staffService.getById(id);
			
			if (staff == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/staff/addNew";
			} else {
				if (staff.getMember().getUid().equals(principal.getName())) {
					reat.addFlashAttribute("message", "Error: Cannot edit self.");
					return "redirect:/admin/staff/view/" + id;
				}
			}
	
			model.addAttribute("staff", staff);
			
			return "admin/staff/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/staff/addNew";
		}
	}

	@PostMapping("/*")
	public String editStaff(@ModelAttribute Staff staff,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		if (staff.getStaffId().equalsIgnoreCase(principal.getName())) {
			reat.addFlashAttribute("message", "Error: Cannot edit self.");
			return "redirect:/admin/staff/view/" + staff.getId();
		}
		
		this.staffEditValidator.validate(staff, result);
		
		if (result.hasErrors()) {
			return "admin/staff/edit";
		}
		
		try {
			TransactionResult tr = this.staffService.updateStaff(staff, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/staff/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/staff/view/"+staff.getId();
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/staff/edit/"+staff.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/staff/edit/"+staff.getId();
		}
	}
}

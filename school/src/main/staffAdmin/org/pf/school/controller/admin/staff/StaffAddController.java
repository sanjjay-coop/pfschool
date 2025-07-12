package org.pf.school.controller.admin.staff;

import java.security.Principal;
import java.util.List;

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
import org.pf.school.validator.add.StaffAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/staff/addNew")
public class StaffAddController extends AdminBaseController {

	@Autowired
	private StaffService staffService;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private GenderRepo genderRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@ModelAttribute("listCategory")
	public List<Category> getListCategory(){
		return this.categoryRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listGender")
	public List<Gender> getListGender(){
		return this.genderRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@ModelAttribute("listRole")
	public List<Role> getListRole(){
		return this.roleRepo.findAll(Sort.by(Sort.Direction.ASC, "code"));
	}
	
	@Autowired
	private StaffAddValidator staffAddValidator;
	
	@GetMapping
	public String staffAdd(Model model) {
		
		Staff staff = new Staff();
		
		model.addAttribute(staff);
		
		return "admin/staff/addNew";
	}
	
	@PostMapping
	public String staffAdd(@ModelAttribute Staff staff,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.staffAddValidator.validate(staff, result);
		
		if (result.hasErrors()) {
			return "admin/staff/addNew";
		}
		
		try {
			TransactionResult tr = this.staffService.addStaff(staff, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/staff/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/staff/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/staff/addNew";
		}
	}
}

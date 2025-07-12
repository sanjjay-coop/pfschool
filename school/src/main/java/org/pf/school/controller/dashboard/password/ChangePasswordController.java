package org.pf.school.controller.dashboard.password;

import java.security.Principal;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.forms.ChangePasswordForm;
import org.pf.school.service.MemberService;
import org.pf.school.validator.ChangePasswordFormValidator;
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
@RequestMapping("/dashboard/changePassword")
public class ChangePasswordController extends DashboardBaseController {

	@Autowired
	private ChangePasswordFormValidator cpfValidator;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping
	public String changePassword(Model model) {
		
		ChangePasswordForm changePasswordForm = new ChangePasswordForm();
		
		model.addAttribute(changePasswordForm);
		
		return "dashboard/password/changePassword";
	}
	
	@PostMapping
	public String changePassword(@ModelAttribute ChangePasswordForm changePasswordForm,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.cpfValidator.validate(changePasswordForm, result);
		
		if (result.hasErrors()) {
			return "dashboard/password/changePassword";
		}
		
		changePasswordForm.setUser(principal.getName());
		
		try {
			TransactionResult tr = this.memberService.changePassword(changePasswordForm, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Password not changed. Please try again later.");
				return "redirect:/dashboard/changePassword";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Password changed successfully.");
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/dashboard/changePassword";
				}
			}
			
			return "redirect:/dashboard";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/dashboard/changePassword";
		}
	}
}

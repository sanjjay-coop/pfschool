package org.pf.school.controller.manager.member;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.manager.ManagerBaseController;
import org.pf.school.model.Member;
import org.pf.school.model.Role;
import org.pf.school.repository.RoleRepo;
import org.pf.school.service.MemberService;
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
@RequestMapping("/manager/member/edit")
public class MemberEditController extends ManagerBaseController {
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private MemberService memberService;
	
	@ModelAttribute("listRole")
	public List<Role> getListRole(){
		return (List<Role>) this.roleRepo.findAll(Sort.by(Sort.Direction.ASC, "code"));
	}
		
	@GetMapping("/{id}")
	public String editMember(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Member member = (Member) this.memberService.getById(id);
			
			if (member == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/manager/member/list/current";
			}
	
			model.addAttribute("member", member);
			
			return "manager/member/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/manager/member/list/current";
		}
	}

	@PostMapping("/*")
	public String editMember(@ModelAttribute Member member,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		if (member.getUid().equals(principal.getName())) {
			reat.addFlashAttribute("message", "Error: Cannot modify self.");
			return "redirect:/manager/member/list/current";
		}
		
		try {
			TransactionResult tr = this.memberService.updateMember(member, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/manager/member/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/manager/member/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/manager/member/edit/"+member.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/manager/member/edit/"+member.getId();
		}
	}
}

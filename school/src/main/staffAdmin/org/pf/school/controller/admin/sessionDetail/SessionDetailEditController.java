package org.pf.school.controller.admin.sessionDetail;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.AcademicSession;
import org.pf.school.model.Member;
import org.pf.school.model.SchoolClass;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.Staff;
import org.pf.school.repository.AcademicSessionRepo;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.RoleRepo;
import org.pf.school.repository.SchoolClassRepo;
import org.pf.school.repository.StaffRepo;
import org.pf.school.service.SessionDetailService;
import org.pf.school.validator.edit.SessionDetailEditValidator;
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
@RequestMapping("/admin/sessionDetail/edit")
public class SessionDetailEditController extends AdminBaseController {


	@Autowired
	private SessionDetailService sessionDetailService;
	
	@Autowired
	private AcademicSessionRepo academicSessionRepo;
	
	@Autowired
	private SchoolClassRepo schoolClassRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@ModelAttribute("listAcademicSession")
	public List<AcademicSession> getListAcademicSession() {
		return this.academicSessionRepo.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
	}
	
	@ModelAttribute("listSchoolClass")
	public List<SchoolClass> getListSchoolClass() {
		return this.schoolClassRepo.findAll(Sort.by(Sort.Direction.ASC, "seqNumber"));
	}
	
	@ModelAttribute("listStaff")
	public List<Staff> getListStaff(){
		
		List<Member> listMember = this.memberRepo.findByRoles(this.roleRepo.findByCodeIgnoreCase("ROLE_STAFF_TEACHER"));		
		List<Staff> listTeacher = this.staffRepo.findByMemberInOrderByNameAsc(listMember);
		
		return listTeacher;
	}
	
	@Autowired
	private SessionDetailEditValidator sessionDetailEditValidator;
	
	@GetMapping("/{id}")
	public String editSessionDetail(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			SessionDetail sessionDetail = (SessionDetail) this.sessionDetailService.getById(id);
			
			if (sessionDetail == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/sessionDetail/addNew";
			}
	
			model.addAttribute("sessionDetail", sessionDetail);
			
			return "admin/sessionDetail/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/sessionDetail/addNew";
		}
	}

	@PostMapping("/*")
	public String editSessionDetail(@ModelAttribute SessionDetail sessionDetail,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.sessionDetailEditValidator.validate(sessionDetail, result);
		
		if (result.hasErrors()) {
			return "admin/sessionDetail/edit";
		}
		
		try {
			TransactionResult tr = this.sessionDetailService.updateSessionDetail(sessionDetail, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/sessionDetail/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/sessionDetail/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/sessionDetail/edit/"+sessionDetail.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/sessionDetail/edit/"+sessionDetail.getId();
		}
	}
}


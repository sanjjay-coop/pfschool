package org.pf.school.controller.admin.sessionDetail;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.Member;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.SessionDetailSubjectTeacher;
import org.pf.school.model.Staff;
import org.pf.school.model.Subject;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.RoleRepo;
import org.pf.school.repository.StaffRepo;
import org.pf.school.repository.SubjectRepo;
import org.pf.school.service.SessionDetailService;
import org.pf.school.service.SessionDetailSubjectTeacherService;
import org.pf.school.validator.SessionDetailSubjectTeacherValidator;
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
@RequestMapping("/admin/sessionDetail/register/subject")
public class RegisterSubjectTeacherController extends AdminBaseController {

	@Autowired
	SessionDetailService sessionDetailService;
	
	@Autowired
	SessionDetailSubjectTeacherService sdsService; 
	
	@Autowired
	SubjectRepo subjectRepo;
	
	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@Autowired
	SessionDetailSubjectTeacherValidator sessionDetailSubjectTeacherValidator;
	
	@ModelAttribute("listStaff")
	public List<Staff> getListStaff(){
		
		List<Member> listMember = this.memberRepo.findByRoles(this.roleRepo.findByCodeIgnoreCase("ROLE_STAFF_TEACHER"));		
		List<Staff> listTeacher = this.staffRepo.findByMemberInOrderByNameAsc(listMember);
		
		return listTeacher;
	}
	
	@ModelAttribute("listSubject")
	public List<Subject> getListSubject(){
		
		List<Subject> listSubject = this.subjectRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
		
		return listSubject;
	}
	
	@GetMapping("/{id}")
	public String registerStudent(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			SessionDetail sessionDetail = (SessionDetail) this.sessionDetailService.getById(id);
			
			if (sessionDetail == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/sessionDetail/list/current";
			}
	
			SessionDetailSubjectTeacher sessionDetailSubjectTeacher = new SessionDetailSubjectTeacher();
			
			sessionDetailSubjectTeacher.setSessionDetail(sessionDetail);
			
			model.addAttribute("sessionDetailSubjectTeacher", sessionDetailSubjectTeacher);
			
			return "admin/sessionDetail/register/subject";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/sessionDetail/list/current";
		}
	}
	
	@PostMapping("/*")
	public String registerStudent(@ModelAttribute SessionDetailSubjectTeacher sessionDetailSubjectTeacher,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.sessionDetailSubjectTeacherValidator.validate(sessionDetailSubjectTeacher, result);
		
		if (result.hasErrors()) {
			return "admin/sessionDetail/register/subject";
		}
		
		try {
			TransactionResult tr = this.sdsService.addSessionDetailSubjectTeacher(sessionDetailSubjectTeacher, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/sessionDetail/view/" + sessionDetailSubjectTeacher.getSessionDetail().getId();
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/sessionDetail/view/" + sessionDetailSubjectTeacher.getSessionDetail().getId();
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/sessionDetail/view/" + sessionDetailSubjectTeacher.getSessionDetail().getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/sessionDetail/view/" + sessionDetailSubjectTeacher.getSessionDetail().getId();
		}
	}
}

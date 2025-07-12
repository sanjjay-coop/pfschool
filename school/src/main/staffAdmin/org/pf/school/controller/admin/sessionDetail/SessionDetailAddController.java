package org.pf.school.controller.admin.sessionDetail;

import java.security.Principal;
import java.util.List;

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
import org.pf.school.validator.add.SessionDetailAddValidator;
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
@RequestMapping(value = "/admin/sessionDetail/addNew")
public class SessionDetailAddController extends AdminBaseController {

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
	private SessionDetailAddValidator sessionDetailAddValidator;
	
	@GetMapping
	public String sessionDetailAdd(Model model) {
		
		SessionDetail sessionDetail = new SessionDetail();
		
		model.addAttribute(sessionDetail);
		
		return "admin/sessionDetail/addNew";
	}
	
	@PostMapping
	public String sessionDetailAdd(@ModelAttribute SessionDetail sessionDetail,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.sessionDetailAddValidator.validate(sessionDetail, result);
		
		if (result.hasErrors()) {
			return "admin/sessionDetail/addNew";
		}
		
		try {
			TransactionResult tr = this.sessionDetailService.addSessionDetail(sessionDetail, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/sessionDetail/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/sessionDetail/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/sessionDetail/addNew";
		}
	}
}


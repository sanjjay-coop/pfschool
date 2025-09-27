package org.pf.school.controller.dashboard.assessment;

import java.security.Principal;
import java.util.List;

import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.forms.AssessmentForm;
import org.pf.school.model.AcademicSession;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.Staff;
import org.pf.school.model.Student;
import org.pf.school.repository.AcademicSessionRepo;
import org.pf.school.repository.SessionDetailRepo;
import org.pf.school.repository.StaffRepo;
import org.pf.school.repository.StudentRepo;
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
@RequestMapping("/dashboard/assessment")
public class DashboardAssessmentController extends DashboardBaseController {

	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@Autowired
	AcademicSessionRepo academicSessionRepo;
	
	@Autowired
	SessionDetailRepo sessionDetailRepo; 
	
	@GetMapping
	public String assessmentView(Model model, RedirectAttributes reat, Principal principal) {
		
		Student student = this.studentRepo.findByMember_Uid(principal.getName());
		
		if (student != null) {
			
			model.addAttribute("student", student);
			
			return "dashboard/assessment/student/view";
		} else {
			
			Staff staff = this.staffRepo.findByMember_Uid(principal.getName());
			
			if (staff != null) {
				
				AssessmentForm assessmentForm = new AssessmentForm();
				
				List<AcademicSession> listAcademicSession = this.academicSessionRepo.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
				
				model.addAttribute("assessmentForm", assessmentForm);
				
				model.addAttribute("listAcademicSession", listAcademicSession);
				
				model.addAttribute("staff", staff);
				
				return "dashboard/assessment/staff/selectSession";
			} else {
				reat.addFlashAttribute("message", "Something went wrong! Contact system administrator.");
				return "redirect:/";
			}
		}
	}
	
	@PostMapping
	public String addAssessment(@ModelAttribute AssessmentForm obj,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		Staff staff = this.staffRepo.findByMember_Uid(principal.getName());
		
		if (staff == null) return "redirect:/";
		
		if (obj.getSession() == null) {
			
			AssessmentForm assessmentForm = new AssessmentForm();
			
			List<AcademicSession> listAcademicSession = this.academicSessionRepo.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
			
			model.addAttribute("assessmentForm", assessmentForm);
			model.addAttribute("listAcademicSession", listAcademicSession);
			model.addAttribute("staff", staff);
			
		} else {
			
			if (obj.getSessionDetail()==null) {
				
				List<SessionDetail> listSessionDetail = this.sessionDetailRepo.findBySessionOrderBySchoolClass_SeqNumberAsc(obj.getSession());
				
				model.addAttribute("listSessionDetail", listSessionDetail);
				model.addAttribute("assessmentForm", obj);
				
				return "dashboard/assessment/staff/selectSessionDetail";
			} else {
				
				if (obj.getSubject()==null) {
					
				} else {
					
					
				}
			}
			
		}
		
		
		return "redirect:/";
	}
}

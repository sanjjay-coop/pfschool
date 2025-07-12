package org.pf.school.controller.dashboard.session;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.model.SessionDetailSubjectTeacher;
import org.pf.school.model.Staff;
import org.pf.school.model.Student;
import org.pf.school.repository.AcademicSessionRepo;
import org.pf.school.repository.SessionDetailRepo;
import org.pf.school.repository.SessionDetailStudentRepo;
import org.pf.school.repository.SessionDetailSubjectTeacherRepo;
import org.pf.school.repository.StaffRepo;
import org.pf.school.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/session/details")
public class DashboardSessionDetailsController extends DashboardBaseController {

	@Autowired
	AcademicSessionRepo sessionRepo;
	
	@Autowired
	SessionDetailRepo sdRepo;
	
	@Autowired
	SessionDetailSubjectTeacherRepo sdstRepo;
	
	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	StaffRepo staffRepo;
	
	@Autowired
	SessionDetailStudentRepo sessionDetailStudentRepo;
	
	@GetMapping
	public String profileView(Model model, RedirectAttributes reat, Principal principal) {
		
		Student student = this.studentRepo.findByMember_Uid(principal.getName());
		
		if (student != null) {
			
			model.addAttribute("student", student);
			
			List<SessionDetailStudent> listSessionDetailStudent = this.sessionDetailStudentRepo.findByStudentOrderBySessionDetail_session_startDateDesc(student);
			
			List<SessionDetail> listSessionDetail = new ArrayList<SessionDetail>();
			
			for(SessionDetailStudent sds : listSessionDetailStudent) {
				listSessionDetail.add(sds.getSessionDetail());
			}
			
			model.addAttribute("listSessionDetail",listSessionDetail);
			
			return "dashboard/session/details/student";
		} else {
			
			Staff staff = this.staffRepo.findByMember_Uid(principal.getName());
			
			if (staff != null) {
				model.addAttribute("staff", staff);
				
				Date today = Calendar.getInstance().getTime();
				
				List<SessionDetail> listSessionDetail = this.sdRepo.listSessionDetail(today, staff);
				
				model.addAttribute("listSessionDetail", listSessionDetail);
				
				List<SessionDetailSubjectTeacher> listSessionDetailSubjectTeacher = this.sdstRepo.listSessionDetailSubjectTeacher(today, staff);
				
				model.addAttribute("listSessionDetailSubjectTeacher", listSessionDetailSubjectTeacher);
				
				return "dashboard/session/details/teacher";
			} else {
				reat.addFlashAttribute("message", "Something went wrong! Contact system administrator.");
				return "redirect:/";
			}
		}
	}
}

package org.pf.school.controller.dashboard.session.teacher;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.forms.AttendanceForm;
import org.pf.school.model.Attendance;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.model.Staff;
import org.pf.school.model.Student;
import org.pf.school.repository.AttendanceRepo;
import org.pf.school.repository.SessionDetailRepo;
import org.pf.school.repository.SessionDetailStudentRepo;
import org.pf.school.repository.StaffRepo;
import org.pf.school.service.AttendanceService;
import org.pf.school.service.SessionDetailService;
import org.pf.school.service.StudentService;
import org.pf.school.validator.AttendanceFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/dashboard/class/student/mark/attendance")
public class DashboardSessionDetailsClassStudentMarkAttendanceController extends DashboardBaseController {

	@Autowired
	SessionDetailRepo sessionDetailRepo;
	
	@Autowired
	SessionDetailService sessionDetailService;
	
	@Autowired
	StaffRepo staffRepo;
	
	@Autowired
	SessionDetailStudentRepo sessionDetailStudentRepo;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	AttendanceFormValidator attendanceFormValidator;
	
	@Autowired
	AttendanceService attendanceService;
	
	@Autowired
	AttendanceRepo attendanceRepo;
	
	@GetMapping("/{id}")
	public String sessionDetailStudentViewController(@PathVariable UUID id, Model model,
			RedirectAttributes reat, Principal principal) {
		
		Staff staff = this.staffRepo.findByMember_Uid(principal.getName());
		
		if (staff==null) {
			reat.addFlashAttribute("message", "No such staff record found.");
			return "redirect:/dashboard";
		}
		
		SessionDetail sessionDetail = this.sessionDetailRepo.findByIdAndClassTeacher(id, staff);
		
		if (sessionDetail == null) {
			reat.addFlashAttribute("message", "No such record is found.");
			return "redirect:/dashboard";
		}
		
		List<SessionDetailStudent> listSessionDetailStudent = this.sessionDetailStudentRepo.findBySessionDetailOrderByStudent_nameAsc(sessionDetail);
		
		model.addAttribute("sessionDetail", sessionDetail);
		model.addAttribute("listSessionDetailStudent", listSessionDetailStudent);
		
		AttendanceForm attendanceForm = new AttendanceForm();
		attendanceForm.setSessionDetailId(id);
		

		Calendar cal = Calendar.getInstance();
		//cal.clear(Calendar.HOUR_OF_DAY);
		//cal.clear(Calendar.MINUTE);
		//cal.clear(Calendar.SECOND);
		//cal.clear(Calendar.MILLISECOND);
		
		Date date = cal.getTime();
		
		attendanceForm.setDate(date);
		
		String[][] data = new String[listSessionDetailStudent.size()][2];
		
		int count = 0;
		
		for(SessionDetailStudent sds : listSessionDetailStudent) {
			
			data[count][0] = sds.getStudent().getId().toString();
			
			Attendance att = this.attendanceRepo.findByDateAndMember(date, sds.getStudent().getMember());
						
			if (att!=null) {

				data[count][1] = att.getStatus();
				
			}
			count++;
		}
		attendanceForm.setData(data);
		
		
		model.addAttribute("attendanceForm", attendanceForm);
		
		return "dashboard/session/details/teacher/class/student/attendance";
	}
	
	@PostMapping("/*")
	public String attendance(@ModelAttribute AttendanceForm attendanceForm,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.attendanceFormValidator.validate(attendanceForm, result);
		
		if (result.hasErrors()) {
			reat.addFlashAttribute("message", result.getAllErrors().toString());
			return "redirect:/dashboard/class/student/mark/attendance/"+attendanceForm.getSessionDetailId();
		}
		
		String[][] data = attendanceForm.getData();
		
		String status = "";
		
		for(int i=0; i < data.length; i++) {
			
			Attendance attendance = new Attendance();
			attendance.setDate(attendanceForm.getDate());
			Student student = (Student)this.studentService.getById(UUID.fromString(data[i][0]));
			attendance.setMember(student.getMember());
			attendance.setStatus(data[i][1]);
			
			try {
				TransactionResult tr = this.attendanceService.updateAttendance(attendance, principal.getName());
				
				if (tr == null) {
					status = status + " :" + data[i][0] + " : Not Added";
				} else if (tr.isStatus()) {
					status = status + " :" + data[i][0] + " : Added Successfully";
				} else {
					status = status + " :" + data[i][0] + " : Not Added";
				}
			} catch (Exception e) {
				status = status + " :" + data[i][0] + " : Not Added & exception " + e.getMessage();
			}
		}
		
		reat.addFlashAttribute("message", status);
		return "redirect:/dashboard/session/details";
	}
}

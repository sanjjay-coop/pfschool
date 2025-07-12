package org.pf.school.controller.dashboard.session.teacher;

import java.security.Principal;
import java.util.Calendar;
import java.util.UUID;

import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.model.Attendance;
import org.pf.school.model.Student;
import org.pf.school.repository.AttendanceRepo;
import org.pf.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard/class/student/attendance")
public class DashboardSessionDetailsStudentAttendanceController extends DashboardBaseController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AttendanceRepo attendanceRepo;
	
	@GetMapping("/{id}/{currentMonth}/{currentYear}/{sessionDetailId}")
	public String studentAttendanceView(@PathVariable UUID id, 
			@PathVariable Integer currentMonth, 
			@PathVariable Integer currentYear,  
			@PathVariable UUID sessionDetailId,
			Model model,
			RedirectAttributes reat, Principal principal) {
			
		
		Student student = (Student) this.studentService.getById(id);
		
		if (student == null) {
			reat.addFlashAttribute("message", "No such a record is found.");
			return "redirect:/dashboard/session/details";
		}
		
		String[][] data = new String[6][7];
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.MONTH, currentMonth);
		cal1.set(Calendar.YEAR, currentYear);
		cal1.set(Calendar.DAY_OF_MONTH, 1);
		
		model.addAttribute("date", cal1.getTime());
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.MONTH, currentMonth);
		cal2.set(Calendar.YEAR, currentYear);
		cal2.set(Calendar.DAY_OF_MONTH, 1);
		cal2.add(Calendar.MONTH, 1);
		
		int row = 0, col = 0;
		
		while(cal1.compareTo(cal2)<0) {
			
			Attendance attendance = this.attendanceRepo.findByDateAndMember(cal1.getTime(), student.getMember());
			
			col = cal1.get(Calendar.DAY_OF_WEEK)-1;
			
			if (attendance != null) {
				data[row][col] = cal1.get(Calendar.DAY_OF_MONTH) + "<br/>" + attendance.getStatus();
			} else {
				data[row][col] = cal1.get(Calendar.DAY_OF_MONTH) + "";
			}
			
			if (col+1 == 7) row++;
			
			cal1.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, currentMonth);
		cal.set(Calendar.YEAR, currentYear);
		cal.add(Calendar.MONTH, 1);
		
		model.addAttribute("nextMonth", cal.get(Calendar.MONTH));
		model.addAttribute("nextYear", cal.get(Calendar.YEAR));
		
		cal.add(Calendar.MONTH, -2);
		
		model.addAttribute("previousMonth", cal.get(Calendar.MONTH));
		model.addAttribute("previousYear", cal.get(Calendar.YEAR));
		
		model.addAttribute("student", student);
		model.addAttribute("data", data);
		model.addAttribute("sessionDetailId", sessionDetailId);
		
		return "dashboard/session/details/teacher/class/studentAttendance";
	}
}

package org.pf.school.controller.admin.sessionDetail;

import java.util.List;
import java.util.UUID;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.model.SessionDetailSubjectTeacher;
import org.pf.school.repository.SessionDetailStudentRepo;
import org.pf.school.repository.SessionDetailSubjectTeacherRepo;
import org.pf.school.service.SessionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/sessionDetail/view")
public class SessionDetailViewController extends AdminBaseController {

	@Autowired
	private SessionDetailService sessionDetailService;
	
	@Autowired
	private SessionDetailStudentRepo sdsRepo;
	
	@Autowired
	private SessionDetailSubjectTeacherRepo sdstRepo;
	
	@GetMapping("/{id}")
	public String editSessionDetail(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			SessionDetail sessionDetail = (SessionDetail) this.sessionDetailService.getById(id);
			
			if (sessionDetail == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/sessionDetail/list/current";
			}
	
			model.addAttribute("sessionDetail", sessionDetail);
			
			List<SessionDetailStudent> listStudent = this.sdsRepo.findBySessionDetailOrderByStudent_nameAsc(sessionDetail);
			
			model.addAttribute("listStudent", listStudent);
			
			List<SessionDetailSubjectTeacher> listSubjectTeacher = this.sdstRepo.findBySessionDetailOrderBySubject_nameAsc(sessionDetail);
			
			model.addAttribute("listSubjectTeacher", listSubjectTeacher);
			
			return "admin/sessionDetail/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/sessionDetail/list/current";
		}
	}
}

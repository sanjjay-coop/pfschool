package org.pf.school.controller.home.academicCalendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.dataDelivery.AcademicCalendarRenderer;
import org.pf.school.model.AcademicCalendar;
import org.pf.school.model.AcademicSession;
import org.pf.school.repository.AcademicCalendarRepo;
import org.pf.school.repository.AcademicSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/academicCalendar")
public class HomeAcademicCalendarRenderController extends HomeBaseController {
	
	@Autowired
	AcademicCalendarRepo academicCalendarRepo;
	
	@Autowired
	private AcademicSessionRepo sessionRepo;

	@GetMapping
	public String renderAcademicCalendar(Model model, RedirectAttributes reat) {
		
		List<AcademicCalendarRenderer> listAcademicCalendarRenderer = new ArrayList<AcademicCalendarRenderer>();
		
		List<AcademicSession> listSession = this.sessionRepo.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
		
		for(AcademicSession session : listSession) {
			
			List<AcademicCalendar> listAcademicCalendar = this.academicCalendarRepo.findAllBySessionOrderByEventDateAsc(session);
			
			AcademicCalendarRenderer acr = new AcademicCalendarRenderer();
			
			acr.setSession(session);
			acr.setListAcademicCalendar(listAcademicCalendar);
			
			listAcademicCalendarRenderer.add(acr);
			
		}
		
		Collections.sort(listAcademicCalendarRenderer);
		
		model.addAttribute("listAcademicCalendarRenderer", listAcademicCalendarRenderer);
		
		return "home/academicCalendar/display";
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "academicCalendar";
	}
	
	
}

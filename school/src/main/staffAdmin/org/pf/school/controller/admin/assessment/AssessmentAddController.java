package org.pf.school.controller.admin.assessment;

import java.security.Principal;
import java.util.List;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.AcademicSession;
import org.pf.school.model.Assessment;
import org.pf.school.repository.AcademicSessionRepo;
import org.pf.school.service.AssessmentService;
import org.pf.school.validator.add.AssessmentAddValidator;
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
@RequestMapping(value = "/admin/assessment/addNew")
public class AssessmentAddController extends AdminBaseController {

	@Autowired
	private AssessmentService assessmentService;

	@Autowired
	private AcademicSessionRepo academicSessionRepo;
	
	@ModelAttribute("listAcademicSession")
	public List<AcademicSession> getListAcademicSession() {
		return this.academicSessionRepo.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
	}
	
	@Autowired
	private AssessmentAddValidator assessmentAddValidator;
	
	@GetMapping
	public String assessmentAdd(Model model) {
		
		Assessment assessment = new Assessment();
		
		model.addAttribute(assessment);
		
		return "admin/assessment/addNew";
	}
	
	@PostMapping
	public String assessmentAdd(@ModelAttribute Assessment assessment,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {
		
		this.assessmentAddValidator.validate(assessment, result);
		
		if (result.hasErrors()) {
			return "admin/assessment/addNew";
		}
		
		try {
			TransactionResult tr = this.assessmentService.addAssessment(assessment, principal.getName());
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
				return "admin/assessment/addNew";
			} else {
				reat.addFlashAttribute("message", "Record added successfully.");
			}
			
			return "redirect:/admin/assessment/addNew";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "admin/assessment/addNew";
		}
	}
}

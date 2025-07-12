package org.pf.school.controller.admin.assessment;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.AcademicSession;
import org.pf.school.model.Assessment;
import org.pf.school.repository.AcademicSessionRepo;
import org.pf.school.service.AssessmentService;
import org.pf.school.validator.edit.AssessmentEditValidator;
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
@RequestMapping("/admin/assessment/edit")
public class AssessmentEditController extends AdminBaseController {

	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private AcademicSessionRepo academicSessionRepo;
	
	@ModelAttribute("listAcademicSession")
	public List<AcademicSession> getListAcademicSession() {
		return this.academicSessionRepo.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
	}
	
	@Autowired
	private AssessmentEditValidator assessmentEditValidator;
	
	@GetMapping("/{id}")
	public String editAssessment(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Assessment assessment = (Assessment) this.assessmentService.getById(id);
			
			if (assessment == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/admin/assessment/addNew";
			}
	
			model.addAttribute("assessment", assessment);
			
			return "admin/assessment/edit";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/admin/assessment/addNew";
		}
	}

	@PostMapping("/*")
	public String editAssessment(@ModelAttribute Assessment assessment,
			BindingResult result, Model model, RedirectAttributes reat,
			Principal principal) {
		
		this.assessmentEditValidator.validate(assessment, result);
		
		if (result.hasErrors()) {
			return "admin/assessment/edit";
		}
		
		try {
			TransactionResult tr = this.assessmentService.updateAssessment(assessment, principal.getName());
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not updated.");
				return "redirect:/admin/assessment/list/current";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Record updated successfully.");
					return "redirect:/admin/assessment/list/current";
				} else {
					reat.addFlashAttribute("message", tr.getMessage());
					return "redirect:/admin/assessment/edit/"+assessment.getId();
				}
			}
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "redirect:/admin/assessment/edit/"+assessment.getId();
		}
	}
}



package org.pf.school.controller.dashboard.assessment;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.dashboard.DashboardBaseController;
import org.pf.school.forms.AssessmentForm;
import org.pf.school.forms.AssessmentMarks;
import org.pf.school.forms.SessionSubjectAssessmentMarks;
import org.pf.school.forms.SubjectAssessmentMarks;
import org.pf.school.model.AcademicSession;
import org.pf.school.model.Assessment;
import org.pf.school.model.AssessmentResult;
import org.pf.school.model.SessionDetail;
import org.pf.school.model.SessionDetailStudent;
import org.pf.school.model.SessionDetailSubjectTeacher;
import org.pf.school.model.Staff;
import org.pf.school.model.Student;
import org.pf.school.model.Subject;
import org.pf.school.repository.AcademicSessionRepo;
import org.pf.school.repository.AssessmentRepo;
import org.pf.school.repository.AssessmentResultRepo;
import org.pf.school.repository.SessionDetailRepo;
import org.pf.school.repository.SessionDetailStudentRepo;
import org.pf.school.repository.SessionDetailSubjectTeacherRepo;
import org.pf.school.repository.StaffRepo;
import org.pf.school.repository.StudentRepo;
import org.pf.school.service.AssessmentResultService;
import org.pf.school.service.SessionDetailStudentService;
import org.pf.school.service.SessionDetailSubjectTeacherService;
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
	
	@Autowired
	SessionDetailStudentRepo sessionDetailStudentRepo;
	
	@Autowired
	SessionDetailStudentService sessionDetailStudentService;
	
	@Autowired
	SessionDetailSubjectTeacherService sessionDetailSubjectTeacherService;
	
	@Autowired
	SessionDetailSubjectTeacherRepo sessionDetailSubjectTeacherRepo;
	
	@Autowired
	AssessmentResultService assessmentResultService;
	
	@Autowired
	AssessmentResultRepo assessmentResultRepo;
	
	@Autowired
	AssessmentRepo assessmentRepo;
	
	@GetMapping
	public String assessmentView(Model model, RedirectAttributes reat, Principal principal) {
		
		Student student = this.studentRepo.findByMember_Uid(principal.getName());
		
		if (student != null) {
			
			model.addAttribute("student", student);
			
			List<SessionSubjectAssessmentMarks> listSsam= new ArrayList<SessionSubjectAssessmentMarks>();
			
			List<SessionDetailStudent> listSds = this.sessionDetailStudentRepo.findByStudentOrderBySessionDetail_session_startDateDesc(student);
			
			for (SessionDetailStudent sds : listSds) {
				
				SessionSubjectAssessmentMarks ssam = new SessionSubjectAssessmentMarks();
				
				ssam.setSession(sds.getSessionDetail().getSession());
				ssam.setSessionDetail(sds.getSessionDetail());
				
				List<Subject> listSubject = this.sessionDetailSubjectTeacherService.getSubject(sds.getSessionDetail());
				
				List<SubjectAssessmentMarks> listSam = new ArrayList<SubjectAssessmentMarks>();
				
				for (Subject subject : listSubject) {
					
					SubjectAssessmentMarks sam = new SubjectAssessmentMarks();
					
					sam.setSubject(subject);
					
					List<AssessmentMarks> listAm = new ArrayList<AssessmentMarks>();
					
					List<Assessment> listAssessment = this.assessmentRepo.findBySessionOrderByNameAsc(sds.getSessionDetail().getSession());
					
					for (Assessment assessment : listAssessment) {
						
						AssessmentMarks am = new AssessmentMarks();
						
						am.setAssessment(assessment);
						
						AssessmentResult ar = this.assessmentResultRepo.findByAssessmentAndStudentAndSubject(assessment, student, subject);
						
						if (ar==null) {
							am.setMarksObtained(0);
							am.setMaxMarks(0);
							
							am.setMarksObtained(0);
							am.setMaxMarks(0);
							
						} else {
							am.setMarksObtained(ar.getMarksObtained());
							am.setMaxMarks(ar.getMaxMarks());
							
							am.setMarksObtained(ar.getMarksObtained());
							am.setMaxMarks(ar.getMaxMarks());
						}
						
						listAm.add(am);
						
					}
					
					sam.setListAssessmentMarks(listAm);
					
					listSam.add(sam);
				}
				
				ssam.setListSubjectAssessmentMarks(listSam);
				
				listSsam.add(ssam);
			}
			
			model.addAttribute("listSsam", listSsam);
			
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
			
			return "dashboard/assessment/staff/selectSession";
		} else {
			
			if (obj.getAssessment()==null) {
				
				List<Assessment> listAssessment = this.assessmentRepo.findBySessionOrderByNameAsc(obj.getSession());
				
				model.addAttribute("listAssessment", listAssessment);
				model.addAttribute("assessmentForm", obj);
				
				return "dashboard/assessment/staff/selectAssessment";
				
			} else {
				if (obj.getSessionDetail()==null) {
					
					List<SessionDetail> listSessionDetail = this.sessionDetailRepo.findBySessionOrderBySchoolClass_SeqNumberAsc(obj.getSession());
					
					model.addAttribute("listSessionDetail", listSessionDetail);
					model.addAttribute("assessmentForm", obj);
					
					return "dashboard/assessment/staff/selectSessionDetail";
				} else {
					
					if (obj.getStudent()==null) {
						List<Student> listStudent = this.sessionDetailStudentService.getStudent(obj.getSessionDetail());
						
						model.addAttribute("listStudent", listStudent);
						model.addAttribute("assessmentForm", obj);
						
						return "dashboard/assessment/staff/selectStudent";
					} else {
						
						if (obj.getSubject()==null) {
							List<Subject> listSubject = this.sessionDetailSubjectTeacherService.getSubject(obj.getSessionDetail());
							
							model.addAttribute("listSubject", listSubject);
							model.addAttribute("assessmentForm", obj);
							
							return "dashboard/assessment/staff/selectSubject";
						} else {
							
							if (obj.getMarksObtained()==null) {
								
								SessionDetailSubjectTeacher sdst = this.sessionDetailSubjectTeacherRepo.findBySessionDetailAndSubject(obj.getSessionDetail(), obj.getSubject());
								
								AssessmentResult ar = this.assessmentResultRepo.findByAssessmentAndStudentAndSubject(obj.getAssessment(), obj.getStudent(), obj.getSubject());
								
								if (ar != null) {
									obj.setMarksObtained(ar.getMarksObtained());
								}
								
								obj.setMaxMarks(sdst.getMaxMarks());
								
								result.rejectValue("marksObtained", "assessmentForm.marksObtained.required");
								
								model.addAttribute("assessmentForm", obj);
								
								return "dashboard/assessment/staff/submitResult";
								
							} else {
								
								if (obj.getMarksObtained()>obj.getMaxMarks()) {
									
									result.rejectValue("marksObtained", "assessmentForm.marksObtained.value");
									
									model.addAttribute("assessmentForm", obj);
									
									return "dashboard/assessment/staff/submitResult";
								} else {
									
									AssessmentResult ar = this.assessmentResultRepo.findByAssessmentAndStudentAndSubject(obj.getAssessment(), obj.getStudent(), obj.getSubject());
									
									if (ar == null) {
										
										// Add new record
										
										ar = new AssessmentResult();
										
										ar.setAssessment(obj.getAssessment());
										ar.setMarksObtained(obj.getMarksObtained());
										ar.setMaxMarks(obj.getMaxMarks());
										ar.setStudent(obj.getStudent());
										ar.setSubject(obj.getSubject());
										
										try {
											TransactionResult tr = this.assessmentResultService.addAssessmentResult(ar, principal.getName());
											
											if (tr == null) {
												reat.addFlashAttribute("message", "Record not added. Please try again later.");
												return "redirect:/dashboard/assessment";
											} else {
												reat.addFlashAttribute("message", "Record added successfully.");
												return "redirect:/dashboard/assessment";
											}
											
										} catch (Exception e) {
											reat.addFlashAttribute("message", e.getMessage());
											return "redirect:/dashboard/assessment";
										}
										
									} else {
										
										// Edit existing record
										
										ar.setMarksObtained(obj.getMarksObtained());
										
										try {
											TransactionResult tr = this.assessmentResultService.updateAssessmentResult(ar, principal.getName());
											if (tr == null) {
												reat.addFlashAttribute("message", "Record not updated.");
												return "redirect:/dashboard/assessment";
											} else {
												reat.addFlashAttribute("message", "Record updated successfully.");
												return "redirect:/dashboard/assessment";
											}
										} catch (Exception e) {
											reat.addFlashAttribute("message", e.getMessage());
											reat.addFlashAttribute("message", "Record added successfully.");
											return "redirect:/dashboard/assessment";
										}
										
									}
									
								}
							}
						}
					}
				}
			}
		}
	}
}

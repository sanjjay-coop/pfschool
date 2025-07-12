package org.pf.school.controller.home;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Carousel;
import org.pf.school.model.Leader;
import org.pf.school.model.News;
import org.pf.school.model.Quotation;
import org.pf.school.model.SchoolClass;
import org.pf.school.model.Staff;
import org.pf.school.repository.CarouselRepo;
import org.pf.school.repository.LeaderRepo;
import org.pf.school.repository.NewsRepo;
import org.pf.school.repository.QuotationRepo;
import org.pf.school.repository.SchoolClassRepo;
import org.pf.school.repository.StaffRepo;
import org.pf.school.repository.StudentRepo;
import org.pf.school.repository.library.TitleRepo;
import org.pf.school.service.SystemService;
import org.pf.school.validator.InitValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController extends HomeBaseController {

	@Autowired
	private SchoolClassRepo schoolClassRepo;
	
	@Autowired
	private CarouselRepo carouselRepo;
	
	@Autowired
	private LeaderRepo leaderRepo;
	
	@Autowired
	private TitleRepo titleRepo;
	
	@Autowired
	private StaffRepo staffRepo;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private InitValidator initValidator;
	
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute("classesRunning")
	public String getClassesRunning() {
		String str = "";
		List<SchoolClass> listSchoolClass = this.schoolClassRepo.findAllByOrderBySeqNumberAsc();
		
		int i = 0;
		
		for(SchoolClass sc : listSchoolClass) {
			if (i==0) str = sc.getCode();
			else str = str + ", " + sc.getCode();
			i++;
		}
		return str;
	}
	
	@ModelAttribute("countTitles")
	public long getCountTitles() {
		return this.titleRepo.count();
	}
	
	@ModelAttribute("countStudents")
	public long getCountStudents() {
		return this.studentRepo.countByMember_Enabled(true);
	}
	
	@ModelAttribute("countStaff")
	public long getCountStaff() {
		return this.staffRepo.countByMember_Enabled(true);
	}
	
	@GetMapping({"/", "/home"})
	public String homeView(Model model, RedirectAttributes reat, Principal principal) {
		
		if (this.staffRepo.count()<1) {
			reat.addFlashAttribute("message", "Kindly provide data to intialize database.");
			return "redirect:/home/initialize";
		}
		
		return "home/index";
	}
	
	@GetMapping("/home/initialize")
	public String initializeView(Model model, RedirectAttributes reat, Principal principal) {
		
		Staff staff = new Staff();
		
		model.addAttribute("staff", staff);
		
		return "home/initialize";
	}
	
	@PostMapping("/home/initialize")
	public String initialize(@ModelAttribute Staff staff,
			BindingResult result, Model model, RedirectAttributes reat, Principal principal) {

		this.initValidator.validate(staff, result);
		
		if (result.hasErrors()) {
			return "home/initialize";
		}
		
		try {
			TransactionResult tr = this.systemService.initializeDatabase(staff);
			
			if (tr == null) {
				reat.addFlashAttribute("message", "Database initialization failed. Please try again later.");
				return "home/initialize";
			} else {
				if (tr.isStatus()) {
					reat.addFlashAttribute("message", "Database initialization completed successfully.");
				} else {
					reat.addFlashAttribute("message", "Database initialization failed. Please try again later.");
				}
			}	
			
			return "redirect:/";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
			return "home/initialize";
		}
	}
	
	@GetMapping("/home/login")
	public String loginView(Model model, RedirectAttributes reat, Principal principal) {
		
		return "home/login";
	}
	
	@ModelAttribute("carouselActive")
	public Carousel getCarouselActive(){
		List<Carousel> listCarousel = this.carouselRepo.listCarouselForPublication(Calendar.getInstance().getTime());
		
		if (listCarousel.isEmpty()) return null;
		else {
			return listCarousel.get(0);
		}
	}
	
	@ModelAttribute("listCarouselDisplay")
	public List<Carousel> getCarouselPhotos(){
		List<Carousel> listCarousel = this.carouselRepo.listCarouselForPublication(Calendar.getInstance().getTime());
		
		if (listCarousel.isEmpty()) return listCarousel;
		else {
			listCarousel.remove(0);
			return listCarousel;
		}
	}

	@Autowired
	QuotationRepo quotationRepo;
	
	@ModelAttribute("loginQuotation")
	public Quotation getLoginQuotation(){
		List<Quotation> listQuotation = this.quotationRepo.findRandomQuotation();
		
		if (listQuotation.isEmpty()) return null;
		return listQuotation.get(0);
	}
	
	@GetMapping("/home/logout-success")
	public String logOutSuccessView(Model model, RedirectAttributes reat, Principal principal) {
		
		List<Leader> listLeader = this.leaderRepo.findRandomLeader();
		
		Leader greatLeader = new Leader();
		
		if (!listLeader.isEmpty()) {
			greatLeader = listLeader.get(0);
		}
		
		model.addAttribute("greatLeader", greatLeader);
		
		return "home/logout";
	}
	
	@Autowired
	private NewsRepo newsRepo;
	
	@ModelAttribute("listNewsLatest")
	public List<News >getListNewsLatest(){
		
		return this.newsRepo.findLatestNews();
	}

	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "home";
	}
}

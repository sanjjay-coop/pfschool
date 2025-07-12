package org.pf.school.controller.library.title;

import java.security.Principal;
import java.util.Calendar;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.controller.library.LibraryBaseController;
import org.pf.school.model.Member;
import org.pf.school.model.library.CheckOut;
import org.pf.school.model.library.Title;
import org.pf.school.repository.MemberRepo;
import org.pf.school.repository.library.CheckOutRepo;
import org.pf.school.service.library.CirculationService;
import org.pf.school.service.library.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/library/title/view")
public class TitleViewController extends LibraryBaseController {
	
	@Autowired
	private TitleService titleService;
	
	@Autowired
	private CirculationService circulationService;
	
	@Autowired
	private CheckOutRepo checkOutRepo;
	
	@Autowired
	private MemberRepo memberRepo;
		
	@GetMapping("/{id}")
	public String viewTitle(@PathVariable UUID id, Model model,
			RedirectAttributes reat) {

		try {
			Title title = (Title) this.titleService.getById(id);
			
			if (title == null) {
				reat.addFlashAttribute("message", "No such record.");
				return "redirect:/library/title/addNew";
			}
			
			CheckOut co = this.checkOutRepo.findByTitle(title);
	
			model.addAttribute("title", title);
			model.addAttribute("co", co);
			
			CheckOut checkOut = new CheckOut();
			
			checkOut.setTitle(title);
			checkOut.setMember(new Member());
			
			model.addAttribute("checkOut", checkOut);
			
			return "library/title/view";
		} catch (Exception e) {
			reat.addFlashAttribute("message", "Record not found.");
			return "redirect:/library/title/addNew";
		} 
	}
	
	@PostMapping("/{id}")
	public String issueTitle(@ModelAttribute CheckOut checkOut, Model model,
			RedirectAttributes reat, Principal principal) {

		if (checkOut.getTitle()==null) {
			reat.addFlashAttribute("message", "No such title is found.");
			return "redirect:/library";
		}
		
		CheckOut co = this.checkOutRepo.findByTitle(checkOut.getTitle());
		
		if (co!=null) {
			reat.addFlashAttribute("message", "Title is already checked-out.");
			return "redirect:/library/title/view/" + checkOut.getTitle().getId();
		}
		
		if (checkOut.getMember()==null || checkOut.getMember().getUid()==null) {
			reat.addFlashAttribute("message", "No such member is found.");
			return "redirect:/library/title/view/" + checkOut.getTitle().getId();
		}
		
		try {
			Member member = this.memberRepo.findByUidIgnoreCase(checkOut.getMember().getUid());
			
			if (member == null) {
				reat.addFlashAttribute("message", "No such member is found.");
				return "redirect:/library/title/view/" + checkOut.getTitle().getId();
			}
			
			if (checkOut.getDueDate()==null) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_YEAR, 30);
				checkOut.setDueDate(cal.getTime());
			}
			
			checkOut.setMember(member);
			
			TransactionResult tr = this.circulationService.addCheckOut(checkOut, principal.getName());
				
			if (tr == null) {
				reat.addFlashAttribute("message", "Record not added. Please try again later.");
			} else if (tr.isStatus()) {
				reat.addFlashAttribute("message", "Record added successfully.");
			} else {
				reat.addFlashAttribute("message", tr.getMessage());
			}
				
		}	catch (Exception e) {
			reat.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/library/title/view/" + checkOut.getTitle().getId();
	}
}

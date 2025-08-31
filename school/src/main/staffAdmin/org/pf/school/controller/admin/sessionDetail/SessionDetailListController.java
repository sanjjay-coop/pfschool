package org.pf.school.controller.admin.sessionDetail;

import java.security.Principal;

import org.pf.school.controller.admin.AdminBaseController;
import org.pf.school.model.SessionDetail;
import org.pf.school.repository.SessionDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/sessionDetail")
public class SessionDetailListController extends AdminBaseController {
	
	@Autowired
	private SessionDetailRepo sessionDetailRepo;
	
	@GetMapping("/list")
	public String listSessionDetail(Model model, Principal principal, HttpServletRequest request) {
		
		int pageNumber = 0;
		
		Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
		
		Page<SessionDetail> page = this.sessionDetailRepo.findAll(pageable);
		
		int totalPages = page.getTotalPages();
		
		model.addAttribute("listSessionDetail", page.getContent());
		
		model.addAttribute("currentPage", pageNumber + 1);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalRecords", page.getTotalElements());
		
		if (pageNumber == 0) model.addAttribute("firstPage", true);
		else model.addAttribute("firstPage", false);
		
		if (pageNumber == (totalPages-1)) {
			model.addAttribute("lastPage", true);
		} else {
			model.addAttribute("lastPage", false);
		}
		
		request.getSession().setAttribute("listSessionDetail_pageNumber", pageNumber);
		request.getSession().setAttribute("listSessionDetail_totalPages", totalPages);
		
		return "admin/sessionDetail/list";
	}
	
	@GetMapping("/list/{whichPage}")
	public String listSessionDetail(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listSessionDetail_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listSessionDetail_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/admin/sessionDetail/list";
				else {
					pageNumber--; 
				}
			} else if ("last".equals(whichPage)) {
				pageNumber = totalPages - 1;
			} else if ("current".equals(whichPage)) {
				
			} else {
				if (pageNumber+1 < totalPages) pageNumber++;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<SessionDetail> page = this.sessionDetailRepo.findAll(pageable);
			
			totalPages = page.getTotalPages();
			
			model.addAttribute("currentPage", pageNumber + 1);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("totalRecords", page.getTotalElements());
			
			if (pageNumber == 0) model.addAttribute("firstPage", true);
			else model.addAttribute("firstPage", false);
			
			if (pageNumber == (totalPages-1)) {
				model.addAttribute("lastPage", true);
			} else {
				model.addAttribute("lastPage", false);
			}
			
			request.getSession().setAttribute("listSessionDetail_pageNumber", pageNumber);
			request.getSession().setAttribute("listSessionDetail_totalPages", totalPages);
			
			model.addAttribute("listSessionDetail", page.getContent());
			
			return "admin/sessionDetail/list";
		
		} catch(Exception e) {
			return "redirect:/admin/sessionDetail/list";
		}
	}
}



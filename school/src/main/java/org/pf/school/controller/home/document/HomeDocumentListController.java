package org.pf.school.controller.home.document;

import java.security.Principal;
import java.util.Calendar;
import java.util.UUID;

import org.pf.school.controller.home.HomeBaseController;
import org.pf.school.model.Document;
import org.pf.school.repository.DocumentRepo;
import org.pf.school.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/home/document")
public class HomeDocumentListController extends HomeBaseController {
	
	@Autowired
	private DocumentRepo documentRepo;
	
	@Autowired
	private DocumentService documentService;
	
	@PostMapping({"/list", "/list/*", "/list/*/*" })
	public String listDocument(@ModelAttribute Document document, Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			request.getSession().setAttribute("homeSearch_document", document);
				
			return "redirect:/home/document/list";
		} catch (Exception e) {
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list")
	public String listDocument(Model model, RedirectAttributes reat, Principal principal, HttpServletRequest request) {
		
		try {
			
			int pageNumber = 0;
			
			Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.DESC, "recordAddDate"));
			
			Page<Document> page;
			
			Document obj = (Document) request.getSession().getAttribute("homeSearch_document");
			
			if (obj == null) {
				page = this.documentRepo.findAllByPubEndDateAfter(Calendar.getInstance().getTime(), pageable);
				obj = new Document();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.documentRepo.findAllByPubEndDateAfter(Calendar.getInstance().getTime(), pageable);
				} else {
					page = this.documentRepo.findByPubEndDateAfterAndSearchStringContainingIgnoreCase(Calendar.getInstance().getTime(), obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("homeSearch_document", obj);
			model.addAttribute("document", obj);
			
			int totalPages = page.getTotalPages();
			
			model.addAttribute("listDocument", page.getContent());
			
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
			
			request.getSession().setAttribute("listDocumentHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listDocumentHome_totalPages", totalPages);
			
			return "home/document/list";
			
		} catch(Exception e) {
			System.out.println("Error Message: " + e);
			reat.addFlashAttribute("message", e);
			return "redirect:/home";
		}
	}
	
	@GetMapping("/list/{whichPage}")
	public String listDocument(@PathVariable String whichPage, Model model, Principal principal, HttpServletRequest request) {
		
		try {
			int pageNumber = (int) request.getSession().getAttribute("listDocumentHome_pageNumber");
			int totalPages = (int) request.getSession().getAttribute("listDocumentHome_totalPages");
			
			if ("previous".equals(whichPage)) {
				if (pageNumber == 0) return "redirect:/home/document/list";
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
			
			Page<Document> page;
			
			Document obj = (Document) request.getSession().getAttribute("homeSearch_document");
			
			if (obj == null) {
				page = this.documentRepo.findAllByPubEndDateAfter(Calendar.getInstance().getTime(), pageable);
				obj = new Document();
				obj.setSearchString("");
			} else {
				if (obj.getSearchFor()==null || obj.getSearchFor().isBlank()) {
					page = this.documentRepo.findAllByPubEndDateAfter(Calendar.getInstance().getTime(), pageable);
				} else {
					page = this.documentRepo.findByPubEndDateAfterAndSearchStringContainingIgnoreCase(Calendar.getInstance().getTime(), obj.getSearchFor(), pageable);
				}
			}
			
			request.getSession().setAttribute("homeSearch_document", obj);
			model.addAttribute("document", obj);
			
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
			
			request.getSession().setAttribute("listDocumentHome_pageNumber", pageNumber);
			request.getSession().setAttribute("listDocumentHome_totalPages", totalPages);
			
			model.addAttribute("listDocument", page.getContent());
			
			return "home/document/list";
		
		} catch(Exception e) {
			return "redirect:/home/document/list";
		}
	}
	
	@ModelAttribute("viewLeftMenu")
	public String viewLeftMenu() {
		return "documents";
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> getSiteLogo(@PathVariable UUID id) {
	    
		System.out.println("Downloading document");
		
		Document document = (Document) this.documentService.getById(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
	        .body(document.getFileData());
	} 
	
}

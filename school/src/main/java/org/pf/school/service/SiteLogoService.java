package org.pf.school.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.SiteLogo;
import org.pf.school.repository.SiteLogoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@Service
public class SiteLogoService {
	
	@Autowired
	private SiteLogoRepo siteLogoRepo;	

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<SiteLogo> oe = this.siteLogoRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addSiteLogo(MultipartFile file, String updateBy) throws IOException {
		
		SiteLogo obj = new SiteLogo();
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		obj.setFileName(fileName);
		obj.setFileType(file.getContentType());
		obj.setFileData(file.getBytes());
		
		obj.setAddDefaults(updateBy);
		
		obj = siteLogoRepo.save(obj);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSiteLogo(UUID id, String updateBy) {

		Optional<SiteLogo> oe = this.siteLogoRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		SiteLogo obj = oe.get();
		
		siteLogoRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSiteLogo(SiteLogo siteLogo, String updateBy) {
		
		Optional<SiteLogo> oe = this.siteLogoRepo.findById(siteLogo.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		SiteLogo obj = oe.get();

		obj.setUpdateDefaults(updateBy);
		
		obj = siteLogoRepo.save(obj);
		
		return new TransactionResult(obj, true);
	}
}

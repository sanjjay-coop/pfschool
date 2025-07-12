package org.pf.school.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.Carousel;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.CarouselRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class CarouselService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private CarouselRepo carouselRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Carousel> oe = this.carouselRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addCarousel(Carousel obj, String updateBy) throws IOException {
		
		obj.setFileName(StringUtils.cleanPath(obj.getFile().getOriginalFilename()));
		obj.setFileType(obj.getFile().getContentType());
		obj.setFileData(obj.getFile().getBytes());
		
		obj.setAddDefaults(updateBy);
		obj = carouselRepo.save(obj);
		
		audit = new Audit(updateBy, "Carousel", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteCarousel(UUID id, String updateBy) {

		Optional<Carousel> oe = this.carouselRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Carousel obj = oe.get();
		
		audit = new Audit(updateBy, "Carousel", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		carouselRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateCarousel(Carousel carousel, String updateBy) throws IOException {
		
		Optional<Carousel> oe = this.carouselRepo.findById(carousel.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Carousel obj = oe.get();
		
		if (!carousel.getFile().isEmpty()) {
			obj.setFileName(StringUtils.cleanPath(carousel.getFile().getOriginalFilename()));
			obj.setFileType(carousel.getFile().getContentType());
			obj.setFileData(carousel.getFile().getBytes());
		}
		
		obj.setDescription(carousel.getDescription());
		obj.setTitle(carousel.getTitle());
		obj.setPubEndDate(carousel.getPubEndDate());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = carouselRepo.save(obj);
		
		audit = new Audit(updateBy, "Carousel", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

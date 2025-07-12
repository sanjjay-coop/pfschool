package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Audit;
import org.pf.school.model.News;
import org.pf.school.repository.AuditRepo;
import org.pf.school.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class NewsService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private NewsRepo newsRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<News> oe = this.newsRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addNews(News obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = newsRepo.save(obj);
		
		audit = new Audit(updateBy, "News", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteNews(UUID id, String updateBy) {

		Optional<News> oe = this.newsRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		News obj = oe.get();
		
		audit = new Audit(updateBy, "News", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		newsRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateNews(News news, String updateBy) {
		
		Optional<News> oe = this.newsRepo.findById(news.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		News obj = oe.get();
		
		obj.setTitle(news.getTitle());
		obj.setContent(news.getContent());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = newsRepo.save(obj);
		
		audit = new Audit(updateBy, "News", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}

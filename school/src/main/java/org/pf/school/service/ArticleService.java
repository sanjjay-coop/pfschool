package org.pf.school.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.pf.school.common.TransactionResult;
import org.pf.school.model.Article;
import org.pf.school.model.Audit;
import org.pf.school.repository.ArticleRepo;
import org.pf.school.repository.AuditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ArticleService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private ArticleRepo articleRepo;

	@Transactional 
	public Object getById(UUID id) {
		if (id == null) return null;
		
		Optional<Article> oe = this.articleRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addArticle(Article obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = articleRepo.save(obj);
		
		audit = new Audit(updateBy, "Article", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteArticle(UUID id, String updateBy) {

		Optional<Article> oe = this.articleRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Article obj = oe.get();
		
		audit = new Audit(updateBy, "Article", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		articleRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateArticle(Article article, String updateBy) {
		
		Optional<Article> oe = this.articleRepo.findById(article.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Article obj = oe.get();
		
		obj.setTitle(article.getTitle());
		obj.setContent(article.getContent());
		obj.setOrgLink(article.getOrgLink());
		obj.setAuthor(article.getAuthor());
		
		obj.setUpdateDefaults(updateBy);
		
		obj = articleRepo.save(obj);
		
		audit = new Audit(updateBy, "Article", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}


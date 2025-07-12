package org.pf.school.repository;

import java.util.List;
import java.util.UUID;

import org.pf.school.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article, UUID>{
	
	Page<Article> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<Article> findByOrgLinkOrderByLinkTitleAsc(Boolean orgLink);
}

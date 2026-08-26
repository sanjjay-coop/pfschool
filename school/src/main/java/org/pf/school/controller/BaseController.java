package org.pf.school.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.pf.school.common.YesNo;
import org.pf.school.model.Advert;
import org.pf.school.model.Article;
import org.pf.school.model.Parameters;
import org.pf.school.repository.AdvertRepo;
import org.pf.school.repository.ArticleRepo;
import org.pf.school.service.ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {
	
	public enum Theme {
		cerulean, cosmo, flatly, journal, lumen, pulse, sandstone, simplex, sketchy, slate, solar, spacelab, united, vapor, yeti, zephyr;
		
		private static final Random PRNG = new Random();

	    public static Theme randomTheme()  {
	        Theme[] themes = values();
	        return themes[PRNG.nextInt(themes.length)];
	    }
	}
	
	@Autowired
	private ParametersService paramsService;
	
	@ModelAttribute("listYesNo")
	public List<YesNo> getListYesNo(){
		
		List<YesNo> listYesNo = new ArrayList<YesNo>();
		
		listYesNo.add(new YesNo(0, "False"));
		listYesNo.add(new YesNo(1, "True"));
		
		return listYesNo;
	}
	
	@ModelAttribute("servletRequest")
	public HttpServletRequest servletRequest(final HttpServletRequest request) {
		
	    return request;
	}

	@ModelAttribute("params")
	public Parameters getParameters() {
		return paramsService.getParameters();
	}

	@Autowired
	private AdvertRepo advertRepo;
	
	@ModelAttribute("listAdvertTop")
	public List<Advert> getListAdvertTop(){
		
		return this.advertRepo.listAdvertForPublication(Calendar.getInstance().getTime(), "top");
	}
	
	@ModelAttribute("listAdvertFooter")
	public List<Advert> getListAdvertFooter(){
		
		return this.advertRepo.listAdvertForPublication(Calendar.getInstance().getTime(), "footer");
	}
	
	@Autowired
	private ArticleRepo articleRepo;
	
	@ModelAttribute("listArticleOrganization")
	public List<Article> getListArticleOrganization(){
		
		return this.articleRepo.findByOrgLinkOrderByLinkTitleAsc(true);
	}
	
	@ModelAttribute("colorTheme")
	public String getColorTheme(HttpServletRequest request) {
		
		String sessionColorTheme = (String) request.getSession().getAttribute("sessionColorTheme");
		
		if (sessionColorTheme == null) {
			Theme theme = Theme.randomTheme();
			sessionColorTheme = theme.toString();
			request.getSession().setAttribute("sessionColorTheme", sessionColorTheme);
		}
		
		return sessionColorTheme;
	}
	
}

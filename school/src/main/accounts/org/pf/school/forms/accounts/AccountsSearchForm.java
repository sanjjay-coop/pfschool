package org.pf.school.forms.accounts;

import java.io.Serializable;

public class AccountsSearchForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7475292388984566034L;

	private String searchFor;
	
	private String searchIn;

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	public String getSearchIn() {
		return searchIn;
	}

	public void setSearchIn(String searchIn) {
		this.searchIn = searchIn;
	}

}

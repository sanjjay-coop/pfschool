package org.pf.school.model;

import java.io.Serializable;
import java.util.UUID;

import org.pf.school.common.BaseObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_quotation")
public class Quotation extends BaseObject implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9198810403166562436L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_quote", length=500, nullable=false)
	private String quote;
	
	@Column(name="f_author", length=100, nullable=false)
	private String author;
	
	@Column(name="f_source", length=500, nullable=true)
	private String source;

	@Transient
	private String searchFor;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Quotation [" + (id != null ? "id=" + id + ", " : "") + (quote != null ? "quote=" + quote + ", " : "")
				+ (author != null ? "author=" + author + ", " : "") + (source != null ? "source=" + source + ", " : "")
				+ (searchFor != null ? "searchFor=" + searchFor : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		this.setSearchString((quote != null ? quote + ", " : "")
				+ (author != null ? author + ", " : "") + (source != null ? source : ""));
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		this.setSearchString((quote != null ? quote + ", " : "")
				+ (author != null ? author + ", " : "") + (source != null ? source : ""));
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
	}
}


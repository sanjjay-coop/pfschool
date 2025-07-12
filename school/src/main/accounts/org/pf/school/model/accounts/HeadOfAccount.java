package org.pf.school.model.accounts;

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

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_accounts_head")
public class HeadOfAccount extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4957802045105114059L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
		
	@Column(name="f_code", length=20, nullable=false, unique=true)
	private String code;
	
	@Column(name="f_name", length=100, nullable=false, unique=true)
	private String name;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "HeadOfAccount [" + (id != null ? "id=" + id + ", " : "") + (code != null ? "code=" + code + ", " : "")
				+ (name != null ? "name=" + name : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((code != null ? code + ", " : "")
				+ (name != null ? name : ""));
		
		this.setCode(this.getCode().toUpperCase());
		this.setName(this.getName().toUpperCase());
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((code != null ? code + ", " : "")
				+ (name != null ? name : ""));
		
		this.setCode(this.getCode().toUpperCase());
		this.setName(this.getName().toUpperCase());
	}

}

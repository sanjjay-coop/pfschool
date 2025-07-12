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

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_role")
public class Role extends BaseObject implements Serializable, Comparable<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6054066407000265127L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_code", length=50, nullable=false, unique=true)
	private String code;
	
	@Column(name="description", length=100, nullable=false, unique=true)
	private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	// compareTo method to sort in 
    // ascending order 
    public int compareTo(Role obj) 
    { 
        return this.code.compareToIgnoreCase(obj.getCode()); 
    } 

	@Override
	public String toString() {
		return "Role [" + (id != null ? "id=" + id + ", " : "") + (code != null ? "code=" + code + ", " : "")
				+ (description != null ? "description=" + description : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.code = this.code.toUpperCase();
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.code = this.code.toUpperCase();
	}

}
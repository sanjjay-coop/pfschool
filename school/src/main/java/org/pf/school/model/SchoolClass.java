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
@Table(name="tab_class")
public class SchoolClass extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3899507585343772605L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_code", length=10, nullable=false, unique=true)
	private String code;
	
	@Column(name="f_name", length=50, nullable=false, unique=true)
	private String name;
	
	@Column(name="f_seq_number_new", nullable=true)
	private Long seqNumber;

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

	public Long getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(Long seqNumber) {
		this.seqNumber = seqNumber;
	}

	@Override
	public String toString() {
		return "SchoolClass [" + (id != null ? "id=" + id + ", " : "") + (code != null ? "code=" + code + ", " : "")
				+ (name != null ? "name=" + name : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.code = this.code.toUpperCase();
		this.name = this.name.toUpperCase();
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.code = this.code.toUpperCase();
		this.name = this.name.toUpperCase();
	}
	
	public String getLabel() {
		return this.getCode() + " [" + this.getName() + "]";
	}
}

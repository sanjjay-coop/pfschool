package org.pf.school.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_audit")
public class Audit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5581676341105006853L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@Column(name="f_target_id", nullable=false)
	private UUID targetId;
	
	@Column(name="f_target_long_id", nullable=true)
	private Long targetLongId;
	
	@Column(columnDefinition = "TEXT", name="f_object_string", nullable=false)
	private String objectString;
	
	@Column(name="f_transaction_date", nullable=false)
	private Date transactionDate;
	
	@Column(name="f_transaction_type", length=50, nullable=false)
	private String transactionType;
	
	@Column(name="f_object_name", length=50, nullable=false)
	private String objectName;
	
	@Column(name="f_object_modified_by", length=50, nullable=false)
	private String objectModifiedBy;

	public Audit() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Audit(String objectModifiedBy, 
			String objectName, 
			String objectString, 
			UUID targetId, 
			Date transactionDate, 
			String transactionType) {
		super();
		this.targetId = targetId;
		this.objectString = objectString;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.objectName = objectName;
		this.objectModifiedBy = objectModifiedBy;
	}
	
	public Audit(String objectModifiedBy, 
			String objectName, 
			String objectString, 
			Long targetLongId, 
			Date transactionDate, 
			String transactionType) {
		super();
		this.targetId = UUID.randomUUID();
		this.targetLongId = targetLongId;
		this.objectString = objectString;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.objectName = objectName;
		this.objectModifiedBy = objectModifiedBy;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getTargetId() {
		return targetId;
	}

	public void setTargetId(UUID targetId) {
		this.targetId = targetId;
	}

	public Long getTargetLongId() {
		return targetLongId;
	}

	public void setTargetLongId(Long targetLongId) {
		this.targetLongId = targetLongId;
	}

	public String getObjectString() {
		return objectString;
	}

	public void setObjectString(String objectString) {
		this.objectString = objectString;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectModifiedBy() {
		return objectModifiedBy;
	}

	public void setObjectModifiedBy(String objectModifiedBy) {
		this.objectModifiedBy = objectModifiedBy;
	}
	
}

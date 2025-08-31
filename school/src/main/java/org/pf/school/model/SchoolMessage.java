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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_message")
public class SchoolMessage extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3443456693486712544L;

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	
	@ManyToOne
    @JoinColumn(name = "f_message_from", nullable=false)
	private Member messageFrom;

	@ManyToOne
    @JoinColumn(name = "f_message_to", nullable=false)
	private Member messageTo;
	
	@Column(name="f_subject", length=100, nullable=false)
	private String subject;
	
	@Column(name="f_message", length=5000, nullable=false)
	private String content;
	
	@Transient
	private String targetAudience;
	
	@Transient
	private SessionDetail targetSession;
	
	@Transient 
	private Boolean sendEmail;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Member getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(Member messageFrom) {
		this.messageFrom = messageFrom;
	}

	public Member getMessageTo() {
		return messageTo;
	}

	public void setMessageTo(Member messageTo) {
		this.messageTo = messageTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTargetAudience() {
		return targetAudience;
	}

	public void setTargetAudience(String targetAudience) {
		this.targetAudience = targetAudience;
	}

	public SessionDetail getTargetSession() {
		return targetSession;
	}

	public void setTargetSession(SessionDetail targetSession) {
		this.targetSession = targetSession;
	}

	public Boolean getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(Boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	@Override
	public String toString() {
		return "Message [" + (id != null ? "id=" + id + ", " : "")
				+ (messageFrom != null ? "messageFrom=" + messageFrom.getUid() + ", " : "")
				+ (messageTo != null ? "messageTo=" + messageTo.getUid() + ", " : "")
				+ (subject != null ? "subject=" + subject + ", " : "") + (content != null ? "content=" + content : "")
				+ "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		this.setSearchString(this.toString());
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		this.setSearchString(this.toString());
	}
	
}

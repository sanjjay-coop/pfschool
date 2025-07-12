package org.pf.school.forms;

import java.io.Serializable;

public class ChangePasswordForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5646172678424552288L;
	private String currentPassword;
	private String newPassword;
	private String retypeNewPassword;
	private String user;
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getRetypeNewPassword() {
		return retypeNewPassword;
	}
	
	public void setRetypeNewPassword(String retypeNewPassword) {
		this.retypeNewPassword = retypeNewPassword;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}	
}


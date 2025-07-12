package org.pf.school.forms;

import java.io.Serializable;

import org.pf.school.model.Member;
import org.pf.school.model.Role;

public class MemberRoleForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2006224264493653293L;
	
	private Member member;
	private Role role;
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}


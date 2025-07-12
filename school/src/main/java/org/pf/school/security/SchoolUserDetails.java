package org.pf.school.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pf.school.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SchoolUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4260612298514310059L;
	
	private Member member;
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	public SchoolUserDetails(Member member, List<GrantedAuthority> authorities) {
		this.member = member;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//System.out.println("Roles: " + authorities);
		return authorities;
	}

	@Override
	public String getPassword() {
		//System.out.println("Password: " + member.getPassword());
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUid();
	}

	@Override
	public boolean isAccountNonExpired() {
		return member.getAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return member.getAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return member.getCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return member.getEnabled();
	}
}
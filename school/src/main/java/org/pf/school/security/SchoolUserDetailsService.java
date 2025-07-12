package org.pf.school.security;

import java.util.ArrayList;
import java.util.List;

import org.pf.school.model.Member;
import org.pf.school.model.Role;
import org.pf.school.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SchoolUserDetailsService implements UserDetailsService {

	
	@Autowired
	private MemberRepo memberRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		if (username == null) {
			throw new UsernameNotFoundException("Username was empty");
		}
		
		Member member = memberRepo.findByUid(username);
		//System.out.println("Searching for Username.");
		
		if (member==null) {
			//System.out.println("Username was not found");
			throw new UsernameNotFoundException("Username was not found");
		}
		
		for(Role role : member.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
		}
		
		return new SchoolUserDetails(member, grantedAuthorities);
	}

}

package com.amazon.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.amazon.common.entity.Role;
import com.amazon.common.entity.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class AmazonUserDetails implements UserDetails {

	static {
		System.out.println("UserDetails class load into memory");
	}
	
	{
		System.out.println("userDetails object created");
	}
	
	
	private User user;
	
	public AmazonUserDetails(User user) {
		System.out.println("COnstructor AUD ************");
		this.user=user;
	}
	
	// get the assigned roles to user
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("get authorities************");
		Set<Role> roles=user.getRoles();
		List<SimpleGrantedAuthority> authorities= new ArrayList<>();
		
		for(Role role: roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		System.out.println("get password************");
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		System.out.println("get username************");
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {		
		System.out.println("is account non expired************");
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		System.out.println("is account non locked************");
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		System.out.println("Is Credential is non expired************");
		return true;
		}
	
	@Override
	public boolean isEnabled() {
		System.out.println("is enables************");
		// TODO Auto-generated method stub
		return user.isEnabled();
	}
	
	public String getFullName() {
		return this.user.getFirstName() + " " + this.user.getLastName();
	}
	
	public void setFirstName(String firstName) {
		this.user.setFirstName(firstName);
	}

	public void setLastName(String firstName) {
		this.user.setLastName(firstName);
	}
	
	
}

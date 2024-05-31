package com.amazon.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.amazon.admin.users.UserRepository;
import com.amazon.common.entity.User;

public class AmazonUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			User user=userRepo.getUserByEmail(email);
			if(user !=null) {
				return new AmazonUserDetails(user);
			}
			throw new UsernameNotFoundException("Could not found the user with email" + email);
	}
}
 
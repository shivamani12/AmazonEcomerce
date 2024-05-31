package com.amazon.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	static {
		System.out.println("PasswordEncoder called");
	}
	
	{
		System.out.println("PasswordEncoder Object created");
	}
	
	@Test
	public void testEncodePassword() {
		System.out.println("TestEncode method Called");
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(); 	
		String rawPass="shiv2020";
		String encodePass=passwordEncoder.encode(rawPass);		
		System.out.println(encodePass);
		
		boolean matches=passwordEncoder.matches(rawPass, encodePass);
		
		assertThat(matches).isTrue();
		
	}
	
	
	

}

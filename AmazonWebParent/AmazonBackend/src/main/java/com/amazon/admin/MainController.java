package com.amazon.admin;

import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	static{
		System.out.println("Main Controller class load");	
		}
		{
			System.out.println("Main Controller class ob created");
		}
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null || authentication instanceof AnonymousAuthenticationToken){
			return "login";
		}
		return "login";
	}
	
	
	
	
}

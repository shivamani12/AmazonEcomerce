package com.amazon.admin.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig{
	static{
		System.out.println("WebSecurityConfig class load");	
		}
		{
			System.out.println("WebSecurityConfig class ob created");
		}
		 	
		@Bean
		public UserDetailsService userDetailsService() {
			System.out.println("UserDetailsService bean  in web security config class*******************************");
			return new AmazonUserDetailsService();
		}
		
	@Bean
	PasswordEncoder passwordEncoder() {
		System.out.println("password encoder bean  in web security config class*******************************");
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		System.out.println("DAO authenticationProvide  in web security config class*******************************");
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		System.out.println("Authentication Manager Builder in web security config class*******************************");
		auth.authenticationProvider(authenticationProvider());
	}
	
	
	@Bean
	SecurityFilterChain configureHttpSecurity(HttpSecurity http) throws Exception {		
		System.out.println("cofngure http http security  bean  in web security config class*******************************");
		
		http.csrf(csrf->csrf.disable()).authorizeHttpRequests(auth->auth.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());	
		
		
//		  http.authorizeHttpRequests(auth-> auth
//		  .requestMatchers("/users/**").hasAuthority("Admin").
//		  requestMatchers("/categories/").hasAnyAuthority("Admin","Editor").anyRequest(
//		  ).authenticated() ) 
//		  .formLogin(form->form
//		 					.loginPage("/login")
//					.usernameParameter("email")
//					.permitAll())
//			.logout(logout-> logout.permitAll())
//			
//			.rememberMe(rem-> rem
//					.key("AbcDefgHijklmnOpqrs_1234567890")
//					.tokenValiditySeconds(7 * 24 * 60 * 60));			
		return http.build();
	}
	
	@Bean
	WebSecurityCustomizer configure() throws Exception {
		System.out.println("WebSecurityCustomizer bean  in web security config class*******************************");
		return (web) ->web.ignoring().requestMatchers("/images/**","/js/**","/webjars/**");
	}
	
}

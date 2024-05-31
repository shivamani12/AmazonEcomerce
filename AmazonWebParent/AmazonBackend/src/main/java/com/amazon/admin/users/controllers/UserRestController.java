package com.amazon.admin.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.admin.users.UserService;

@RestController
public class UserRestController {

		@Autowired
		UserService service;
	
		@PostMapping("/users/check_email")
		public String checkDuplicateEmail(@Param("id")Integer id,@Param("email") String email) {
			System.out.println(id);
			System.out.println(email);
			return service.isEmailUnique(id,email) ? "OK" : "Duplicated";
		}
	
}

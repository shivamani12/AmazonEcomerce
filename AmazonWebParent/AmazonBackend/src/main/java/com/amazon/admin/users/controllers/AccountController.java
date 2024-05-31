package com.amazon.admin.users.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazon.admin.FileUploadUtil;
import com.amazon.admin.security.AmazonUserDetails;
import com.amazon.admin.users.UserService;
import com.amazon.common.entity.User;


	@Controller
	public class AccountController {
	
		
		@Autowired
		private UserService service;
		
		// for view account details form
		@GetMapping("/account")
		public String viewDetails(@AuthenticationPrincipal AmazonUserDetails loggedUser, Model model) {
			String email=loggedUser.getUsername();
			User user=service.getByEmail(email);
			model.addAttribute("user",user);
			return "/users/account_form";
			
		}
	
		// for update the account 
		@PostMapping("/account/update")
		public String newUser(User user, RedirectAttributes redirectAttributes,
				@AuthenticationPrincipal AmazonUserDetails loggedUser,
				@RequestParam("image") MultipartFile multipartFile) throws IOException {

			if (!multipartFile.isEmpty()) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

				user.setPhotos(fileName); // we are storing the file name only in db

				User savedUser = service.updateAccount(user);

				String uploadDir = "user-photos/" + savedUser.getId(); // now we are tring to upload the picture wiht id as
																		// a subdirectory into file system
				FileUploadUtil.cleanDir(uploadDir);
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			}
			else {
				if (user.getPhotos().isEmpty())
					user.setPhotos(null);
				service.updateAccount(user);
			}
			loggedUser.setFirstName(user.getFirstName());
			loggedUser.setLastName(user.getLastName());
			redirectAttributes.addFlashAttribute("message", "Your account details has been updated successfully.");
			return "redirect:/account";
		}
	
}

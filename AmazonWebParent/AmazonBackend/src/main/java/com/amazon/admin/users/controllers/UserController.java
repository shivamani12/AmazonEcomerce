package com.amazon.admin.users.controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.amazon.admin.FileUploadUtil;
import com.amazon.admin.users.UserService;
import com.amazon.admin.users.exceptions.UserNotFoundException;
import com.amazon.admin.users.export.UserCsvExporter;
import com.amazon.common.entity.Role;
import com.amazon.common.entity.User;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
// it will handle the request from user module
public class UserController {

	@Autowired
	UserService service;

	@GetMapping("/users")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "firstName", "asc", null);
	}

	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") Integer pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {

		System.out.println("Sort Field : " + sortField);
		System.out.println("Sort Order : " + sortDir);

		Page<User> page = service.listByPage(pageNum, sortField, sortDir, keyword);
		List<User> listUsers = page.getContent();

		// return to the front end
		long startCount = (pageNum - 1) * UserService.USER_PER_PAGE + 1; // it is like (1-1)=0 * 4 =0 0+1=1;
		long endCount = startCount + UserService.USER_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"; // when u click users so by default first page
																		// is asc order so based on
		// we just make a desc string with method for further use...
		System.out.println(reverseSortDir);

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);

		// we are sending sorting memebers so based on condition front end will take
		// this and process
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);

		return "/users/users";
	}

	
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> roles = service.listAllRoles();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", roles);
		model.addAttribute("pageTitle", "Create New User");
		System.out.println(user);
		System.out.println(roles);
		return "users/user_form";
	}
	
	
	@PostMapping("/users/save")
	public String newUser(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile,HttpServletRequest request) throws IOException, MessagingException {

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

			user.setPhotos(fileName); // we are storing the file name only in db

			User savedUser = service.save(user,getSiteURL(request));
			System.out.println(request.getRequestURI().toString()+"getRequestURI+++++++++++++++++++++++++++++++++++++++++++++++++++++");
			String uploadDir = "user-photos/" + savedUser.getId(); // now we are tring to upload the picture wiht id as
																	// a subdirectory into file system
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		else {
			if (user.getPhotos().isEmpty())
				user.setPhotos(null);
			service.save(user,getSiteURL(request));
		}
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		return "redirect:/users";
	}
	
	private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        System.out.println(siteURL+"siteURL*********************************************");
        System.out.println(request.getServletPath()+"Get Servlet PAth*********************************************");
        return siteURL.replace(request.getServletPath(), "");
    }  
	
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code,Model model) {
		
		if(service.verify(code)) {
			return "verify_success";
		}else {
			return "verify_fail";
		}
		
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes ra) {
		try {
			User user = service.get(id);
			List<Role> roles = service.listAllRoles();
			model.addAttribute("user", user);
			model.addAttribute("listRoles", roles);
			model.addAttribute("pageTitle", "Edit User (ID: " + id + ") ");
			return "/users/user_form";
		} catch (UserNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/users";
	}

	
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes ra) {

		try {
			service.delete(id);
			ra.addFlashAttribute("message", "The user ID " + id + " has been deleted successfully");
		} catch (UserNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/users";
	}

	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserStatus(@PathVariable(name = "id") Integer id, @PathVariable(name = "status") boolean status,
			RedirectAttributes ra) {
		service.updateUserEnablesStatus(id, status);
		String enabled = status ? "enabled" : "disabled";
		String message = "The user Id: " + id + " has been " + enabled + " !!";
		ra.addFlashAttribute("message", message);
		return "redirect:/users";
	}

	
	@GetMapping("/users/export/csv")
	public void exportToCsv(HttpServletResponse response) throws IOException {
		List<User> listUsers = service.listAll();
		UserCsvExporter exporter = new UserCsvExporter();
		exporter.export(listUsers, response);

	}

}

package com.amazon.admin.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CategoryController {

	@GetMapping("/categories")
	public String categories() {
		
		return "/category/categories";
	}
	
	@GetMapping("/new")
	public String createNew(Model model) {
		model.addAttribute("pageTitle", "Create New Category");
		return "category/category_form";
	}
	
	
	
}

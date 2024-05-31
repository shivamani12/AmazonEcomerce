package com.amazon.admin.category;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.amazon.admin.FileUploadUtil;
import com.amazon.common.entity.Category;
import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/api")
public class CategoryRestController {
	Logger logger;
	
	@Autowired
	CategoryService service;
	
	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> listAll(){
		System.out.println("Controller");
		List<Category> list =service.listAll();
			
		return new ResponseEntity<List<Category>>(list,HttpStatus.OK);
	}
 	
	@GetMapping("/categories/form")
	public ResponseEntity<List<Category>> listCategoriesUsedInForm(){
		List<Category> list =service.listCategoriesUsedInForm();
		
		return new ResponseEntity<List<Category>>(list,HttpStatus.OK);
	}
	
	@PostMapping("/categories/save")
	public ResponseEntity<String> saveCategory(@RequestParam("name") String name,
	                                           @RequestParam("alias") String alias,
	                                           @RequestParam("enabled") boolean enabled,
	                                           @RequestParam("image") MultipartFile image,
	                                           	@RequestParam("parentCategory")Category parent,
												HttpServletRequest request, @RequestBody Category cat) throws IOException {
		System.out.println(cat.toString());
		System.out.println(request.getRequestURI());
	 	Category category = new Category();
	    category.setName(name);
	    category.setAlias(alias);
	    category.setEnabled(enabled);
	    category.setParent(null);
	   
	    if (!image.isEmpty()) {
			String fileName = StringUtils.cleanPath(image.getOriginalFilename());

			category.setImage(fileName); // we are storing the file name only in db

			Category savedcategory = service.saveCat(category);
			String uploadDir = "category-photos/" + savedcategory.getId(); // now we are tring to upload the picture wiht id as
																	// a subdirectory into file syste
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, image);
		}
		else {
			if (category.getImage().isEmpty())
				category.setImage(null);
			service.saveCat(category);
		}// Set other properties as needed

	    service.saveCat(category);

	    // Return a success response
	    return ResponseEntity.ok("Category created successfully.");
	}

 	
 	
 	
 	
	
}

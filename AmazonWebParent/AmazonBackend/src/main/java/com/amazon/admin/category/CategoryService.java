package com.amazon.admin.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazon.common.entity.Category;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository catRepo;
	
	
	public List<Category> listAll(){
		System.out.println("service");
		 return (List<Category>) catRepo.findAll();
	}
	
	
	public List<Category> listCategoriesUsedInForm(){
		
		List<Category> categoriesUsedInForm=new ArrayList<Category>();
			
		Iterable<Category> categoriesInDB=catRepo.findAll();
		
		for(Category category : categoriesInDB) {
	
			if(category.getParent()==null) {
				categoriesUsedInForm.add(new Category(category.getName()));
			Set<Category> children=category.getChildren();
			
		for(Category subCategory :children) {
			System.out.println("--" +subCategory.getName());
			String name="--" + subCategory.getName();
			categoriesUsedInForm.add(new Category(name));
			listChildren(categoriesUsedInForm,subCategory,1);
		}
		}
			
	}
		return categoriesUsedInForm;
		}
	
	private void listChildren( List<Category> categoriesUsedInForm,Category parent, int sublevel) {
			int newSubLevel=sublevel+1;
			
			Set<Category>children=parent.getChildren();
			
			for(Category subCategory : children) {
				String name="";
				for(int i=0; i<newSubLevel; i++) {
					name +="--";
				}
				name += subCategory.getName();
				categoriesUsedInForm.add(new Category(name));
				System.out.println(subCategory.getName());
				listChildren(categoriesUsedInForm,subCategory, newSubLevel);
			}
			
	}
	
	public Category saveCat(Category category) {
		Category saved=catRepo.save(category);
		return saved;
	}
	
	
	
}

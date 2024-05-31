package com.amazon.admin.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.amazon.common.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>{

	
}

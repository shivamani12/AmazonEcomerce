package com.amazon.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.amazon.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE) // we want to run the test against 
//the real database, not the in-memory database which is used by default.

@Rollback(false)
//And we want to keep the data persisted into the database.
//So we use the annotation rollback with the property value false here.
public class CategoryRepositoryTest {

	
	@Autowired
	CategoryRepository repo;
	
//	@Test
//	public void testCreateRootCategory() {
//			
//		Category rootCategory=new Category("Electronics");
//		Category savedCategory=repo.save(rootCategory);
//		assertThat(savedCategory.getId()).isGreaterThan(0);
//	}
//	
//	@Test
//	public void testSubCategory() {
//		Category parent= new Category(102);
//		Category subCategory= new Category("Camera",parent);
//		Category saved=repo.save(subCategory);
//		assertThat(saved.getId()).isGreaterThan(0);
//		
//		
//	}
}

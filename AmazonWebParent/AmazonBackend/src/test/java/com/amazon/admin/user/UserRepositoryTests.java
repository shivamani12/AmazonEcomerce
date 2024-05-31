package com.amazon.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.amazon.admin.users.RoleRepository;
import com.amazon.admin.users.UserRepository;
import com.amazon.common.entity.Role;
import com.amazon.common.entity.User;

@DataJpaTest(showSql = false) // specify JPA specified test class
@AutoConfigureTestDatabase(replace=Replace.NONE) // replace embedded in memory db to actual db
@Rollback(false) 	// disable automatic transaction rollback after each test
public class UserRepositoryTests {

	static{
	System.out.println("test UserRepository load");	
	}
	{
		System.out.println("test UserRepository ob created");
	}
	@Autowired
	private UserRepository repo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private TestEntityManager entityManager;   // provides method for interacting with db 
	 
	@Test
	public void testcreateUserWithOneROle() {   // TEST CREATE USER FOR one ROLE
		
		Role roleAdmin=entityManager.find(Role.class,1);   // fetching the role entity from db so, it can associate with user entity 
		
		User userShivam=new User("shiv@gmail.com","Blackpearl","Shivam","Tomar");
		
		userShivam.addRole(roleAdmin);
		
		User savedUser=repo.save(userShivam);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateUserWithTwoRole() {
		
				User userAmit=new User("pl@gmail.com","pl","pl","SIngh");
				
				Role roleEditor=new Role(3);
				Role roleAssistant=new Role(5);
				System.out.println(roleEditor);
				System.out.println(roleAssistant);
				userAmit.addRole(roleEditor);
				userAmit.addRole(roleAssistant);
				
				User savedUser=repo.save(userAmit);
				
				assertThat(savedUser.getId()).isGreaterThan(0);
			
	}
	
	@Test
	public void testListAllUsers() {
		
		Iterable<User> UserList=repo.findAll();
		
			UserList.forEach(e-> System.out.println(e));
			
		
	}
	
	@Test
	public void testGetUserById() {

		User userShiv=repo.findById(5).get();		
		System.out.println(userShiv);
		assertThat(userShiv).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetail() {
		
		User userShiv=repo.findById(1).get();
		userShiv.setEnabled(true);
		userShiv.setEmail("stomar492@gmail.com");
		repo.save(userShiv);
	}
	
	@Test
	public void testUpdateUserRole() {
				
			User userAmit=repo.findById(30).get();
			
			Role roleEdit=new Role(3);
			
			Role roleSalesPerson=new Role(2);
			System.out.println(roleRepo.findById(3).toString()+"->"+roleRepo.findById(3).hashCode());
			System.out.println(roleEdit.toString()+"= "+roleEdit.hashCode());
			System.out.println(roleRepo.findById(3).get()==roleEdit);
			System.out.println(roleRepo.findById(3).get().equals(roleEdit));
			userAmit.getRoles().remove(roleEdit);    
		//	userAmit.getRoles().remove(roleRepo.findById(3).get()); // we can also do like that when i dont i want to create new object and compare with other
			userAmit.addRole(roleSalesPerson);
			repo.save(userAmit);
			// without equals method they are comparing address so both object has different location like 3==3 , both are in different localtion
			// with equals method they are comparing content(id) roleEditor.equalsTo(roleEdit) it is true beceause they have same id which is content
			// it is comparing this-> Role [id=3, name=Editor, description=manage categories, brands, products,articles and menus]
//			.equalsTo(Role [id=3, name=null, description=null]) 
	}
	
	@Test
	public void testDeleteUser() {
			Integer userId=3;
			repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
			String email="stomar492@gmail.com";
			User user=repo.getUserByEmail(email);
			System.out.println(user);
			assertThat(user).isNotNull();
	}
	

	@Test
	public void testCountById() {
			
			Integer userId=152;
			
			Long countById=repo.countById(userId);
			
			assertThat(countById).isNotNull().isGreaterThan(0);
		
	}
	
	@Test
	public void testDisableUser() {
		Integer id=44;
		repo.updateEnabledStatus(id, false);	
	}
	
	@Test
	public void testEnableUser() {
		Integer id=44;
		repo.updateEnabledStatus(id, true);	
	}
	
	@Test
	public void testListFirstPage() {
		
		int pageNumber=1;
		int pageSize=4;
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize);  // pageable contains the information or refer to a object about page 
				
		Page<User> page=repo.findAll(pageable);
		
		List<User> listUser=page.getContent();
		
		listUser.forEach(user-> System.out.println(user));
		
		assertThat(listUser.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearch() {
		String keyword="tomar";
		
		int pageNumber=0;
		int pageSize=4;
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize);  // pageable contains the information or refer to a object about page 
        Page<User> page=repo.findAll(keyword,pageable);
		
		List<User> listUser=page.getContent();
		listUser.forEach(user-> System.out.println(user));
		
		assertThat(listUser.size()).isGreaterThan(0);
	}
		
}

package com.amazon.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.amazon.admin.users.RoleRepository;
import com.amazon.common.entity.Role;

@DataJpaTest 
@AutoConfigureTestDatabase(replace=Replace.NONE) // in memory database will be replaced with actual database
@Rollback(false)
public class RoleRepositoryTests {

	static {
		System.out.println(" class load-RoleTest");
	}
	{
		System.out.println(" object create- RoleTest");
	}
	

	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin=new Role("Admin","manage everything");
		Role savedRole=repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
		// assert =>perform a basic validation in the test method to ensure that when a Role entity is saved using the RoleRepository, it receives a positive and non-zero ID,
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleSalesperson=new Role("Salesperson","manage product price, " + "customers, shipping, orders and sales report");
		Role roleEditor=new Role("Editor","manage categories, brands," + " products,articles and menus");
		Role roleShipper=new Role("Shipper","view products , view orders " + "and update order status");
		Role roleAssistant=new Role("Assistant", "manage questions and reviews");
		 
		repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
		// we dont need assertion in that

	}
}

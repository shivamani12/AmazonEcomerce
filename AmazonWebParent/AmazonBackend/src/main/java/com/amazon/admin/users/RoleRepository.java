package com.amazon.admin.users;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.amazon.common.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{

	
	
}

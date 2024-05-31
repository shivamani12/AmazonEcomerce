package com.amazon.common.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// this is common entity for backend and frontend

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 40, nullable = false, unique = true)
	private String name;

	@Column(length = 150, nullable = false, unique = true)
	private String description;

	static {
		System.out.println(" Role class Entity load-Role");
	}
	{
		System.out.println(" Role Entity object create- Role");
	}
	
	public Role(){
		System.out.println("blank Constructor- ROle Entity");
	}
	public Role(String name){
		this.name=name;
	}
	
	public Role(String name, String description) {
		System.out.println("Param Constructor- ROle Entity");
		this.name = name;
		this.description = description;
	}

	
	public Role(Integer id) {
		super();
		this.id = id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		System.out.println("get name role entity");
		return name;
	}

	public void setName(String name) {
		System.out.println("Set name role entity");
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public boolean equals(Object obj) {
		System.out.println(this.toString());
		System.out.println(obj.toString());
		if (this == obj) {
			System.out.println("first");
			return true;
		}
		if (obj == null) {
			System.out.println("second");
			return false;
		}
		if (getClass() != obj.getClass()) {
			System.out.println("third");
			return false;
		}
		Role other = (Role) obj;{
			System.out.println(other);
			System.out.println(obj);
			System.out.println("forth");
		return Objects.equals(id, other.id);
		}
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
	
	
}

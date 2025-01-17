package com.amazon.common.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="categories")
public class Category {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=128, nullable=true, unique=true)
	private String name;
	
	@Column(length=64, nullable=true, unique=true)
	private String alias;
	
	@Column(length=128, nullable=true)
	private String image;
	
	private boolean enabled;
	
	
	@OneToOne
	@JoinColumn(name="parent_id")
	@JsonIgnore 
	private Category parent;
	
	@OneToMany(mappedBy="parent")
	private Set<Category> children=new HashSet<>();
	
	public Category(String name) {
		this.name=name;	
		this.alias=name;
		this.image="default.png";
	}
	public Category(String name,Category parent) {
		this(name);
		this.parent=parent;
	}

	public Category() {
		super();
	}
	
	public Category(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public  String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParent() {
		return parent;
	}

		public void setParent(Category parent) {
			this.parent = parent;
		}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	
	
}

package com.amazon.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="users")
public class User {

	
	public Integer getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(Integer verificationCode) {
		this.verificationCode = verificationCode;
	}


	static{
		System.out.println("User Entity class load");	
		}
		{
			System.out.println("User Entity class ob created");
		}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=128, nullable=false, unique=true )
	private String email;
	
	@Column(length=64, nullable=false)
	private String password;
	
	@Column(name="first_name",length=45, nullable=false)
	private String firstName;
	
	@Column(name="last_name", length=45, nullable=false)
	private String lastName;
	
	@Column(length=64)
	private String photos;
	
	@Column(name="verification_code", length=64)
	private Integer verificationCode;
	
	
	private boolean enabled;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="users_roles",
			joinColumns =@JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
			
			)  // for intermediate table
	private Set<Role> roles=new HashSet<Role>();
	

	public User() {
		
	}
	
	// method
	public void addRole(Role role) {
		System.out.println("addROleMethod Called in user");
		this.roles.add(role);
	}
	
	public User(String email, String password, String firstName, String lastName) {
		System.out.println("User param constructor called");
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		System.out.println("User getId method called");
		return id;
	}

	public void setId(Integer id) {
		System.out.println("User SetId method called");
		this.id = id;
	}

	public String getEmail() {
		System.out.println("User getEmail method called");
		return email;
	}

	public void setEmail(String email) {
		System.out.println("User SetEmail method called");
		this.email = email;
	}

	public String getPassword() {
		System.out.println("User get password method called");
		return password;
	}

	public void setPassword(String password) {
		System.out.println("User set pass method called");
		this.password = password;
	}

	public String getFirstName() {
		System.out.println("User get first name method called");
		return firstName;
	}

	public void setFirstName(String firstName) {
		System.out.println("User setFirstName method called");
		this.firstName = firstName;
	}

	public String getLastName() {
		System.out.println("User getLastName method called");
		return lastName;
	}

	public void setLastName(String lastName) {
		System.out.println("User setLastName method called");
		this.lastName = lastName;
	}

	public String getPhotos() {
		System.out.println("User getPhotos method called");
		return photos;
	}

	public void setPhotos(String photos) {
		System.out.println("User setPhotos method called");
		this.photos = photos;
	}

	public boolean isEnabled() {
		System.out.println("User isENables method called");
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		System.out.println("User setENabled method called");
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		System.out.println("User getROles method called");
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		System.out.println("User setRoles method called");
		this.roles = roles;
	}
	public String getFullName() {
		return this.getFirstName() + " " + this.getLastName();
	}
	@Transient
	public String getPhotosImagePath() {
		System.out.println("getPhotosImagePath Called**************************");
		if( id==null || photos == null) return "/images/default-user.png";
		
			return "/user-photos/" +this.id + "/" + this.photos;
		}
	
	
	
	@Override
	public String toString() {
		System.out.println("User toString method called");
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", photos=" + photos + ", enabled=" + enabled + ", roles=" + roles + "]";
	}

	
	
	
}

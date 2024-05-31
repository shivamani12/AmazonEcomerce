package com.amazon.admin.users;

import java.io.UnsupportedEncodingException;

import java.util.List;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amazon.admin.users.exceptions.UserNotFoundException;
import com.amazon.common.entity.Role;
import com.amazon.common.entity.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

	static {
		System.out.println("UserService class load");
	}

	{
		System.out.println("UserService Object created");
	}

	public static final int USER_PER_PAGE = 4;

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	JavaMailSender mailSender;

	// GET USERS
	public List<User> listAll() {
		return (List<User>) userRepo.findAll(Sort.by("firstName").ascending());
	}

	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {

		Sort sort = Sort.by(sortField);
		
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE, sort); // pageNum-1 (1-1=0) // so bring 0 page ,
																				// 4 element (0,4)
								
		if (keyword != null) {
			return userRepo.findAll(keyword, pageable);
		}
		return userRepo.findAll(pageable);

	}

	// GET ROLES
	public List<Role> listAllRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	// SAVE USER
	public User save(User user, String siteUrl) throws UnsupportedEncodingException, MessagingException {

		boolean isUpdateUser = user.getId() != null;

		if (isUpdateUser) {

			User existingUser = userRepo.findById(user.getId()).get();
			System.out.println("==========================" + existingUser);
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);
			Integer randomCode=(int) Math.random();
		    user.setVerificationCode(randomCode);
		    user.setEnabled(false);
		    sendVerificationMail(user, siteUrl);
		}
		 return userRepo.save(user);
	}
	
	private void sendVerificationMail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
		 String toAddress = user.getEmail();
		    String fromAddress = "Admin@gmail.com";
		    String senderName = "AmazonAdminPanel";
		    String subject = "Please verify your registration";
		    String content = "Dear [[name]],<br>"
		            + "Please click the link below to verify your registration:<br>"
		            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
		            + "Thank you,<br>"
		            + "Your company name."; 	
		     
		    MimeMessage message = mailSender.createMimeMessage();
		    System.out.println(message);
		    MimeMessageHelper helper = new MimeMessageHelper(message);
		     
		    helper.setFrom(fromAddress, senderName);
		    helper.setTo(toAddress);
		    helper.setSubject(subject);
		     
		    content = content.replace("[[name]]", user.getFullName());
		    String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
		    System.out.println(verifyURL+" VerifyUrl#################################################");
		    content = content.replace("[[URL]]", verifyURL);
		    System.out.println(content+"Content#################################################"); 
		    helper.setText(content, true);
		    
		    mailSender.send(message);
		     
		}
	
	public boolean verify(String Vcode) {
			
			User user=userRepo.findByVerificationCode(Vcode);
			
			if(user==null || user.isEnabled()){  
				return false;
			}else {
				user.setVerificationCode(null);
				user.setEnabled(true);
				userRepo.save(user);
				return true;
			}
			
	}

	// PASSWORD ENCODE
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	// EMAIL UNIQUE
	public boolean isEmailUnique(Integer id, String email) {

		User userByEmail = userRepo.getUserByEmail(email);

		if (userByEmail == null)
			return true;

		boolean isCreatingNew = (id == null);

		if (isCreatingNew) {

			if (userByEmail != null)
				return false;
		} else {
			// when you update the user-> edit mode

			if (userByEmail.getId() != id) {
				return false;
			}
		}

		return true;
	}

	// GET USER BY ID
	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("Could not found any user with ID " + id);
		}

	}

	// Delete user
	public void delete(Integer id) throws UserNotFoundException {

		Long countById = userRepo.countById(id);

		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not found any user with ID " + id);
		}
		userRepo.deleteById(id);

	}

	// update status
	public void updateUserEnablesStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}

	// this method will be call in account controller
	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}

	//
	public User updateAccount(User userInform) {
		User userInDB = userRepo.findById(userInform.getId()).get();

		if (!userInform.getPassword().isEmpty()) {
			encodePassword(userInDB);
		}

		if (userInform.getPhotos() != null) {
			userInDB.setPhotos(userInform.getPhotos());
		}

		userInDB.setFirstName(userInform.getFirstName());
		userInDB.setLastName(userInform.getLastName());
		return userRepo.save(userInDB);
	}

}

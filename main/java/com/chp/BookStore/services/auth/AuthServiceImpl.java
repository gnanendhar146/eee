package com.chp.BookStore.services.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chp.BookStore.dto.SignupRequest;
import com.chp.BookStore.dto.UserDto;
import com.chp.BookStore.entity.User;
import com.chp.BookStore.enums.UserRole;
import com.chp.BookStore.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		if(adminAccount == null) {
			User user = new User();
			user.setName("admin");
			user.setEmail("admin@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("admin123"));
			user.setUserRole(UserRole.ADMIN);
			userRepository.save(user);;
			System.out.println("Admin Created");
		}
	}
	
	
	@Override
	public UserDto createCustomer(SignupRequest signupRequest) {
		
		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		User createUser = userRepository.save(user);
		UserDto userDto = new UserDto();
		userDto.setId(createUser.getId());
		return userDto;
	
	}

	@Override
	public boolean hasCustomerWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
	
	
}

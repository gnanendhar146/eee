package com.chp.BookStore.services.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chp.BookStore.entity.User;
import com.chp.BookStore.repository.UserRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository, UserRepository userRepository2) {
		this.userRepository = userRepository2;
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// write logic to get user from db
		Optional<User>optionalUser = userRepository.findFirstByEmail(email);
		if(optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found",null);
		return new org.springframework.security.core.userdetails.User(optionalUser.get().getEmail(), optionalUser.get().getPassword(), new ArrayList<>());
	}

}

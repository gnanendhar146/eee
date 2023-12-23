package com.chp.BookStore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chp.BookStore.dto.AuthenticationRequest;
import com.chp.BookStore.dto.AuthenticationResponse;
import com.chp.BookStore.dto.SignupRequest;
import com.chp.BookStore.dto.UserDto;
import com.chp.BookStore.entity.User;
import com.chp.BookStore.repository.UserRepository;
import com.chp.BookStore.services.auth.AuthService;
import com.chp.BookStore.services.jwt.UserDetailsServiceImpl;
import com.chp.BookStore.utils.JWTUtils;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/auth/")
public class AuthController {

	private final AuthService authService;
	
	private final AuthenticationManager authenticationManager;
	
	private final UserDetailsServiceImpl userDetailsService;

	private final JWTUtils jwtUtil;
	
	private final UserRepository userRepository;
	
	public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsServiceImpl, UserDetailsServiceImpl userDetailsServicel, UserDetailsServiceImpl userDetailsService, JWTUtils jwtUtil, UserRepository userRepository) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> sinupCustomer(@RequestBody SignupRequest signupRequest){
	
	    if(authService.hasCustomerWithEmail(signupRequest.getEmail()))
	    	  return new ResponseEntity<>("customer already exists with this email",HttpStatus.NOT_ACCEPTABLE);
		
	  UserDto createCustomerDto =	authService.createCustomer(signupRequest);
	  if(createCustomerDto == null) return new ResponseEntity<> 
	                              ("Customer not created, come again later", HttpStatus.BAD_REQUEST);
	  return new ResponseEntity<> (createCustomerDto, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response)throws IOException, java.io.IOException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new BadCredentialsException("Incorrect username or password");
		}catch (DisabledException disabledException) {
			// TODO: handle exception
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not active");
			return null;
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails);
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		if(optionalUser.isPresent()) {
			authenticationResponse.setJwt(jwt);
			authenticationResponse.setUserRole(optionalUser.get().getUserRole());
			authenticationResponse.setUserId(optionalUser.get().getId());
		}
		return authenticationResponse;
	}
	
}

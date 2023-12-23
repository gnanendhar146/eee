package com.chp.BookStore.services.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chp.BookStore.dto.SignupRequest;
import com.chp.BookStore.dto.UserDto;
import com.chp.BookStore.entity.User;

@Service
public interface AuthService {

	UserDto createCustomer(SignupRequest signupRequest);

	boolean hasCustomerWithEmail(String email);
}

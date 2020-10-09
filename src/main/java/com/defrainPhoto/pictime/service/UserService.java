package com.defrainPhoto.pictime.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.defrainPhoto.pictime.model.User;
import com.defrainPhoto.pictime.repository.UserRegistrationDto;

public interface UserService extends UserDetailsService {
	
	User findByEmail(String email);
	
	User save(UserRegistrationDto registration);

	User findById(Long id);
}

package com.anish.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anish.weather.repository.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	// This method returns a 'User' object from the database which matches the given username, or throws an exception
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Calling the function to find if a user exists with the given username
		UserDetails user = userRepository.findByUsername(username);
		
		// If user by the given username exists
		if(user != null)
			return user;
		
		// If a user with the given username doesn't exist
		throw new UsernameNotFoundException("User with username " + username + "not found");
	}
}

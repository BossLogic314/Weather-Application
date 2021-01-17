package com.anish.weather.repository;

import com.anish.weather.model.User;

public interface UserRepository {

	// This method finds a user in the database with the given username
	public User findByUsername(String username);
	
	// This method returns the number of users with a given username
	public Integer findNumOfUsers(String username);
	
	// This method saves a 'User' object in the database and returns it
	public User save(User user);
}

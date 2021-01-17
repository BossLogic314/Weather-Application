package com.anish.weather.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegistrationForm {

	// Attributes
	@NotBlank(message = "Firstname cannot be empty.")
	@Size(max = 15, message = "Firstname can only be upto 15 characters in length.")
	private String FirstName;
	
	@NotBlank(message = "Lastname cannot be empty.")
	@Size(max = 15, message = "Lastname can only be upto 15 characters in length.")
	private String LastName;
	
	@Size(min = 5, message = "Username cannot be less than 5 characters in length.")
	@Size(max = 20, message = "Username can only be upto 20 characters in length.")
	private String Username;
	
	@Size(min = 5, message = "Password cannot be less than 5 characters in length.")
	@Size(max = 20, message = "Password can only be upto 20 characters in length.")
	private String Password;
	
	// Getters and setters
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	// Returns a User object with the information in the registration form
	public User toUser() {
		
		// Returning a new 'User' object
		return new User(FirstName, LastName, Username, Password);
	}
}

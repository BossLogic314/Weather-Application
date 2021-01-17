package com.anish.weather.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

	// Camel case is not followed in defining the attributes because this is how the columns are stored in the database
	
	// Attributes
	private Long UserId;
	private String FirstName;
	private String LastName;
	private String Username;
	private String Password;
	
	// Constructor 1
	public User(String FirstName, String LastName, String Username, String Password) {
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.Username = Username;
		this.Password = Password;
	}
	
	// Constructor 2
	public User(Long userId, String FirstName, String LastName, String Username, String Password) {
		this.UserId = userId;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.Username = Username;
		this.Password = Password;
	}
	
	// Getters and setters
	public Long getUserId() {
		return UserId;
	}

	public void setUserId(Long userId) {
		UserId = userId;
	}
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

	public void setUsername(String username) {
		Username = username;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	@Override
	// Returning that all the people have the same authority which is 'User'
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return Password;
	}

	@Override
	public String getUsername() {
		return Username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

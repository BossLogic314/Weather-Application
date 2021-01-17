package com.anish.weather.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anish.weather.model.RegistrationForm;
import com.anish.weather.repository.UserRepository;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public String getRegistrationForm(Model model) {
		
		model.addAttribute("registrationForm", new RegistrationForm());
		return "registrationForm";
	}
	
	@PostMapping
	// This method takes the data from the registration form and creates a new 'User' in the database
	public String processRegistrationForm(Model model, @Valid RegistrationForm registrationForm, Errors errors) {
		
		// If any errors in the registration form are detected, return the registration form
		if(errors.hasErrors())
			return "registrationForm";
		
		// The the returned object is a valid user, then a user by the username exists
		Integer numOfUsers = userRepository.findNumOfUsers(registrationForm.getUsername());
		
		// If a user exists with the entered username, returning back the registration form
		if(numOfUsers != 0) {
			model.addAttribute("uniqueUser", false);
			return "registrationForm";
		}
		
		// Saving the new user in the database
		userRepository.save(registrationForm.toUser());
		
		return "redirect:/login";
	}
}

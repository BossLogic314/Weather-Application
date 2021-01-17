package com.anish.weather.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anish.weather.model.CityWeatherInfo;
import com.anish.weather.model.User;
import com.anish.weather.service.CityWeatherService;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins="*")
public class CityResponseController {

	@Autowired
	private CityWeatherService cityWeatherService;
	
	@PostMapping("/citydetails")
	public CityWeatherInfo cityResponse(@RequestParam("cityid") Long cityId, Authentication auth) throws Exception {
		
		// Getting the current user
		User user = (User) auth.getPrincipal();
		
		// Saving the city
		return cityWeatherService.saveUserCity(user, cityId);
	}
	
	@DeleteMapping("/citydetails")
	public void deleteCityOfUser(@RequestParam("cityid") Long cityId, Authentication auth) throws Exception {
		
		// Getting the current user
		User user = (User) auth.getPrincipal();
		
		// Calling the function to delete the city from the user's collection
		cityWeatherService.deleteCityFromUser(user, cityId);
	}
	
	@DeleteMapping("/removeallcities")
	public void deleteAllCitiesOfUser(Authentication auth) throws Exception {
		
		// Getting the current user
		User user = (User) auth.getPrincipal();

		// Calling the function to delete all cities from the user's collection
		cityWeatherService.deleteAllCitiesOfUser(user);
	}
}

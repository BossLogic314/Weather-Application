package com.anish.weather.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anish.weather.model.CityWeatherInfo;
import com.anish.weather.model.User;
import com.anish.weather.service.CityWeatherService;

@Controller
@RequestMapping("/weather")
public class WeatherController {
	
	@Autowired
	CityWeatherService cityWeatherService;

	@GetMapping
	public String getWeather(Model model, Authentication auth) throws Exception {
		
		// Getting the current user
		User user = (User) auth.getPrincipal();
		
		// Getting all the current cities
		Iterable<CityWeatherInfo> listOfCitiesOfUser = cityWeatherService.getAllUserCities(user);
		
		// Adding the objects into the model
		model.addAttribute("cityList", listOfCitiesOfUser);
		model.addAttribute("userName", user.getFirstName());
		
		return "weather";
	}
}

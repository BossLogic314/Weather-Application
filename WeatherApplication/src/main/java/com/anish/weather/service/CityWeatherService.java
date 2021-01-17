package com.anish.weather.service;

import com.anish.weather.model.CityWeatherInfo;
import com.anish.weather.model.User;

public interface CityWeatherService {
	
	// This function returns the weather attributes for the city
	public Iterable<CityWeatherInfo> getCityWeather(Iterable<Long> cityIdList) throws Exception;

	// This function saves a city for a user
	public CityWeatherInfo saveUserCity(User user, Long cityId) throws Exception;
	
	// This function returns the attributes of all the cities of the user
	public Iterable<CityWeatherInfo> getAllUserCities(User user) throws Exception;
	
	// This function deletes a city from the collection of the user
	public void deleteCityFromUser(User user, Long cityId);
	
	// This function deletes all the cities from the user's collection
	public void deleteAllCitiesOfUser(User user);
}

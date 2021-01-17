package com.anish.weather.repository;

import com.anish.weather.model.User;

public interface UserCitiesRepository {

	// This function saves a city for a user
	public void saveUserCity(User user, Long cityId);
	
	// This function returns all the city names of a user
	public Iterable<Long> getAllUserCities(User user);
	
	// This function deletes a city from the collection of the user
	public void deleteCityFromUser(User user, Long cityId);
	
	// This function deletes all the cities from the user's collection
	public void deleteAllCitiesOfUser(User user);
}

package com.anish.weather.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.anish.weather.model.CityWeatherInfo;
import com.anish.weather.model.User;
import com.anish.weather.model.WeatherResponse;
import com.anish.weather.repository.UserCitiesRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CityWeatherServiceImpl implements CityWeatherService {
	
	@Autowired
	UserCitiesRepository userCitiesRepository;
	
	// Parts of the URL to make a call to the weather API
	private String urlPart1 = "https://api.openweathermap.org/data/2.5/group?id=";
	private String urlPart2 = "&units=metric&appid=";
	private String key = "58c382442bf6fbeecf85827cb90f3afa";
	
	// This function returns the weather attributes for the city
	public Iterable<CityWeatherInfo> getCityWeather(Iterable<Long> cityIdList) throws Exception {
		
		// This is the URL to which a call has to be made to retrieve the JSON data of the city's weather
		String urlString = urlPart1;
		
		// This is the iterator of cityIdList
		Iterator<Long> iterator = cityIdList.iterator();
		
		// Appending the whole required URL to the string
		while(true) {
			
			// If there are more cityIds to append, doing it
			if(iterator.hasNext()) {
				
				urlString += iterator.next().toString() + ',';
				
				continue;
			}
			
			// If there are no more city ids left to append, breaking out of the loop
			break;
		}
		
		// Appending the end part of the URL
		urlString += urlPart2 + key;
		
		// Defining a new URL object for making API calls
		URL url = new URL(urlString);
		
		// Making a connection
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		// Setting that a GET request is to be made
		connection.setRequestMethod("GET");
		
		// The data received is in JSON format
		connection.setRequestProperty("Content-Type", "application/json");
		
		// Reading all the JSON data received in a buffer
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		String inputLine;
		StringBuffer buff = new StringBuffer();
		
		// Reading each line from the Buffered Reader and appending it to a string to store the information
		while((inputLine = bufferedReader.readLine()) != null)
			buff.append(inputLine);
		
		// Returning an object which stores the necessary weather details about the city
		return convertJsonToCityWeatherInfo(buff);
	}

	@Override
	// This function saves a city for a user
	public CityWeatherInfo saveUserCity(User user, Long cityId) throws Exception {
		
		// This is the list to send to get the weather of the city
		ArrayList<Long> inputCityIdList = new ArrayList<Long>();
		
		// Adding the cityId to the list
		inputCityIdList.add(cityId);
		
		// Getting the weather details of the city
		Iterable<CityWeatherInfo> cityWeatherInfo = getCityWeather(inputCityIdList);
		
		// Getting the list of all the cities in the user's collection
		Iterable<Long> cityIdList = userCitiesRepository.getAllUserCities(user);
		
		// If the city to be saved is already present in the list, do not save
		for(Long id : cityIdList) {
			
			if(id.equals(cityId))
				return cityWeatherInfo.iterator().next();
		}
		
		// Calling the function in the repository
		userCitiesRepository.saveUserCity(user, cityId);
		
		return cityWeatherInfo.iterator().next();
	}

	@Override
	// This function returns all the city names in the collection of a user
	public Iterable<CityWeatherInfo> getAllUserCities(User user) throws Exception {
		
		// Storing all the names of the cities in the user's collection
		Iterable<Long> cityIdList = userCitiesRepository.getAllUserCities(user);
		
		// If there are no cities to call initially, return an empty list without calling the weather API
		if(!cityIdList.iterator().hasNext())
			return new ArrayList<CityWeatherInfo>();
		
		// Returning the details of all the cities of the user
		return getCityWeather(cityIdList);
	}

	@Override
	public void deleteCityFromUser(User user, Long cityId) {
		
		// Calling the function in the repository
		userCitiesRepository.deleteCityFromUser(user, cityId);
	}

	@Override
	public void deleteAllCitiesOfUser(User user) {
		
		// Calling the function in the repository
		userCitiesRepository.deleteAllCitiesOfUser(user);
	}
	
	// This processes the weather icon
	private String finalWeatherIcon(String weatherIcon) {
		
		if(weatherIcon.equals("03d") || weatherIcon.equals("03n"))
			return "03";
		if(weatherIcon.equals("04d") || weatherIcon.equals("04n"))
			return "04";
		if(weatherIcon.equals("09d") || weatherIcon.equals("09n"))
			return "09";
		if(weatherIcon.equals("11d") || weatherIcon.equals("11n"))
			return "11";
		if(weatherIcon.equals("13d") || weatherIcon.equals("13n"))
			return "13";
		if(weatherIcon.equals("50d") || weatherIcon.equals("50n"))
			return "50";
		
		return weatherIcon;
	}
	
	// This function converts the information from JSON format to CityWeatherInfo
	private Iterable<CityWeatherInfo> convertJsonToCityWeatherInfo(StringBuffer buff) throws Exception {
		
		// Used to map the JSON data into an object of 'CityResponse' class
		ObjectMapper objectMapper = new ObjectMapper();
		
		// If any unknown attributes are encountered, just ignore them
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// Copying the data into a 'WeatherResponse' object
		WeatherResponse weatherResponse = objectMapper.readValue(buff.toString(), WeatherResponse.class);
		
		// Stores a list of CityWeatherInfo objects
		ArrayList<CityWeatherInfo> cityWeatherInfoList = new ArrayList<CityWeatherInfo>();
		
		for(int i = 0; i < weatherResponse.cnt; ++i) {
		
			// Storing the list which contains all the details
			WeatherResponse.WeatherItem weatherItemInner = weatherResponse.list.get(i);

			// Obtaining the temperature of the city
			Double temp = weatherItemInner.main.getTemp();
			temp = Math.round(temp * 10) / 10.0;

			// Getting and processing the weather icon
			String weatherIcon = weatherItemInner.weather.get(0).getIcon();
			weatherIcon = finalWeatherIcon(weatherIcon);

			// Capitalizing the first letter in the description
			String weatherDescription = StringUtils.capitalize(weatherItemInner.weather.get(0).getDescription());

			// Constructing a new object with the required attributes
			CityWeatherInfo cityWeatherInfo = new CityWeatherInfo(weatherItemInner.name, weatherItemInner.id, temp,
					weatherDescription, weatherIcon);
			
			cityWeatherInfoList.add(cityWeatherInfo);
		}
		
		// Returning the list of all CityWeatherInfo objects
		return cityWeatherInfoList;
	}
}

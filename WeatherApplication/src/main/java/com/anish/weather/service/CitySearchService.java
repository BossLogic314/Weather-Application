package com.anish.weather.service;

import com.anish.weather.model.CitySearchInfo;

public interface CitySearchService {

	// This function searches for all the possible cities with the text which was typed so far
	public Iterable<CitySearchInfo> getPossibleCities(String cityText);
}

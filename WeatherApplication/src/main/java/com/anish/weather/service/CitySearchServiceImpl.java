package com.anish.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anish.weather.model.CitySearchInfo;
import com.anish.weather.repository.CitySearchRepository;

@Service
public class CitySearchServiceImpl implements CitySearchService {

	@Autowired
	private CitySearchRepository citySearchRepository;
	
	@Override
	// This function searches for all the possible cities with the text which was typed so far
	public Iterable<CitySearchInfo> getPossibleCities(String cityText) {
		return citySearchRepository.getPossibleCities(cityText);
	}
}

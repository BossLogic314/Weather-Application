package com.anish.weather.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anish.weather.model.CitySearchInfo;
import com.anish.weather.service.CitySearchService;

@RestController
@RequestMapping("/citysearch")
public class CitySearchController {
	
	@Autowired
	private CitySearchService citySearchService;
	
	@GetMapping
	// This function searches for all the possible cities with the text which was typed so far
	public Iterable<CitySearchInfo> getAllPossibleCities(@RequestParam("cityText") String cityText) {
		return citySearchService.getPossibleCities(cityText);
	}
}

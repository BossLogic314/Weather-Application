package com.anish.weather.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.anish.weather.model.CitySearchInfo;

@Repository
public class JdbcCitySearchRepositoryImpl implements CitySearchRepository {
	
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	// This function searches for all the possible cities with the text which was typed so far
	public Iterable<CitySearchInfo> getPossibleCities(String cityText) {
		
		return jdbc.query("select top 30 * from CityList where CityName like '" + cityText + "%'", this::mapRowToCitySearchInfo);
	}
	
	// This function maps the rows of the result set into objects object
	private CitySearchInfo mapRowToCitySearchInfo(ResultSet rs, int rowNum) throws SQLException {
		return new CitySearchInfo(rs.getString("CityName"), rs.getLong("CityId"), rs.getString("CountryName"),
				rs.getString("CountryCode"), rs.getDouble("Latitude"), rs.getDouble("Longitude"));
	}
}

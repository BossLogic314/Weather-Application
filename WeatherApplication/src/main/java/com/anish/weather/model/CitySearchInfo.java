package com.anish.weather.model;

public class CitySearchInfo {

	// Attributes
	public String cityName;
	public Long cityId;
	public String countryName;
	public String countryCode;
	public Double latitude;
	public Double longitude;
	
	// Constructor
	public CitySearchInfo(String cityName, Long cityId, String countryName, String countryCode, Double latitude, Double longitude) {
		this.cityName = cityName;
		this.cityId = cityId;
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	// Getters and setters
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}

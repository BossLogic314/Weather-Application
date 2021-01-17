package com.anish.weather.model;

public class CityWeatherInfo {

	// Attributes
	private String cityName;
	private Long cityId;
	private Double temperature;
	private String description;
	private String icon;
	
	// Constructor
	public CityWeatherInfo(String cityName, Long cityId, Double temperature, String description, String icon) {
		this.cityName = cityName;
		this.cityId = cityId;
		this.temperature = temperature;
		this.description = description;
		this.icon = icon;
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
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}

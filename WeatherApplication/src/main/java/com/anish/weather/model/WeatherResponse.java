package com.anish.weather.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {
	
	// Empty constructor
	public WeatherResponse() {
		
	}

	public Long cnt;
	public List<WeatherItem> list = new ArrayList<WeatherItem>();
	
	public static class WeatherItem{
		public Coordinates coord;
		public Sys sys;
		public List<Weather> weather = new ArrayList<Weather>();
		public Main main;
		public Long visibility;
		public Wind wind;
		public Clouds clouds;
		public Long dt;
		public Long id;
		public String name;
		
		// Empty constructor
		public WeatherItem() {
			
		}
	
		public class Coordinates {
			private Double lon;
			private Double lat;
			
			public Double getLon() {
				return lon;
			}
			public void setLon(Double lon) {
				this.lon = lon;
			}
			public Double getLat() {
				return lat;
			}
			public void setLat(Double lat) {
				this.lat = lat;
			}
		}
		
		public class Sys {
			private String country;
			private Long timezone;
			private Long sunrise;
			private Long sunset;
			
			public String getCountry() {
				return country;
			}
			public void setCountry(String country) {
				this.country = country;
			}
			public Long getTimezone() {
				return timezone;
			}
			public void setTimezone(Long timezone) {
				this.timezone = timezone;
			}
			public Long getSunrise() {
				return sunrise;
			}
			public void setSunrise(Long sunrise) {
				this.sunrise = sunrise;
			}
			public Long getSunset() {
				return sunset;
			}
			public void setSunset(Long sunset) {
				this.sunset = sunset;
			}
		}
		
		public static class Weather {
			private Long id;
			private String main;
			private String description;
			private String icon;
			
			// Empty constructor
			public Weather() {
				
			}
			
			public Long getId() {
				return id;
			}
	
			public void setId(Long id) {
				this.id = id;
			}
	
			public String getMain() {
				return main;
			}
	
			public void setMain(String main) {
				this.main = main;
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
		
		public class Main {
			private Double temp;
			private Double feels_like;
			private Double temp_min;
			private Double temp_max;
			private Long pressure;
			private Long humidity;
			
			public Double getTemp() {
				return temp;
			}
			public void setTemp(Double temp) {
				this.temp = temp;
			}
			public Double getFeels_like() {
				return feels_like;
			}
			public void setFeels_like(Double feels_like) {
				this.feels_like = feels_like;
			}
			public Double getTemp_min() {
				return temp_min;
			}
			public void setTemp_min(Double temp_min) {
				this.temp_min = temp_min;
			}
			public Double getTemp_max() {
				return temp_max;
			}
			public void setTemp_max(Double temp_max) {
				this.temp_max = temp_max;
			}
			public Long getPressure() {
				return pressure;
			}
			public void setPressure(Long pressure) {
				this.pressure = pressure;
			}
			public Long getHumidity() {
				return humidity;
			}
			public void setHumidity(Long humidity) {
				this.humidity = humidity;
			}
		}
		
		public class Wind {
			private Double speed;
			private Long deg;
			
			public Double getSpeed() {
				return speed;
			}
			public void setSpeed(Double speed) {
				this.speed = speed;
			}
			public Long getDeg() {
				return deg;
			}
			public void setDeg(Long deg) {
				this.deg = deg;
			}
		}
		
		public class Clouds {
			private Long all;
	
			public Long getAll() {
				return all;
			}
	
			public void setAll(Long all) {
				this.all = all;
			}
		}
	}
}
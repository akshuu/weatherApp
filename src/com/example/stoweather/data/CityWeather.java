
package com.example.stoweather.data;

import java.util.List;

public class CityWeather{
   	private CloudInfo clouds;
   	private CoordInfo coord;
   	private Number distance;
   	private Number dt;
   	private Number id;
   	private Temperature main;
   	private String name;
   	private List<WeatherInfo> weather;
   	private WindInfo wind;

 	public CloudInfo getClouds(){
		return this.clouds;
	}
	public void setClouds(CloudInfo clouds){
		this.clouds = clouds;
	}
 	public CoordInfo getCoord(){
		return this.coord;
	}
	public void setCoord(CoordInfo coord){
		this.coord = coord;
	}
 	public Number getDistance(){
		return this.distance;
	}
	public void setDistance(Number distance){
		this.distance = distance;
	}
 	public Number getDt(){
		return this.dt;
	}
	public void setDt(Number dt){
		this.dt = dt;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public Temperature getTemperature(){
		return this.main;
	}
	public void setTemperature(Temperature main){
		this.main = main;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public List<WeatherInfo> getWeatherInfo(){
		return this.weather;
	}
	public void setWeatherInfo(List<WeatherInfo> weather){
		this.weather = weather;
	}
 	public WindInfo getWind(){
		return this.wind;
	}
	public void setWind(WindInfo wind){
		this.wind = wind;
	}
}

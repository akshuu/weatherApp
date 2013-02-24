
package com.example.stoweather.data;

import java.util.List;

public class WeatherInfo{
   	private String description;
   	private String icon;
   	private int id;
   	private String main;

 	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
 	public String getIcon(){
		return this.icon;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
 	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
 	public String getMain(){
		return this.main;
	}
	public void setMain(String main){
		this.main = main;
	}
}

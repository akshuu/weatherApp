
package com.example.stoweather.data;

import java.util.List;

public class WeatherData{
 	private String calctime;
   	private int cnt;
   	private String cod;
   	public String getCalctime() {
		return calctime;
	}
	public void setCalctime(String calctime) {
		this.calctime = calctime;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private String message;
   	private List<CityWeather> list;
   	
 	public List<CityWeather> getList(){
		return this.list;
	}
	public void setList(List<CityWeather> list){
		this.list = list;
	}
}

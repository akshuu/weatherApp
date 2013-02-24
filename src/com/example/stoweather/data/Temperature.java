
package com.example.stoweather.data;

import java.util.List;

public class Temperature{
   	private float humidity;
   	private float pressure;
   	private float temp;
   	private float temp_max;
   	private float temp_min;

 	public float getHumidity(){
		return this.humidity;
	}
	public void setHumidity(float humidity){
		this.humidity = humidity;
	}
 	public float getPressure(){
		return this.pressure;
	}
	public void setPressure(float pressure){
		this.pressure = pressure;
	}
 	public float getTemp(){
		return this.temp;
	}
	public void setTemp(float temp){
		this.temp = temp;
	}
 	public float getTemp_max(){
		return this.temp_max;
	}
	public void setTemp_max(float temp_max){
		this.temp_max = temp_max;
	}
 	public float getTemp_min(){
		return this.temp_min;
	}
	public void setTemp_min(float temp_min){
		this.temp_min = temp_min;
	}
}

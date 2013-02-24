
package com.example.stoweather.data;

import java.io.Serializable;

public class WindInfo implements Serializable{
	
	
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   	private float deg;
   	private float speed;

 	public float getDeg(){
		return this.deg;
	}
	public void setDeg(float deg){
		this.deg = deg;
	}
 	public float getSpeed(){
		return this.speed;
	}
	public void setSpeed(float speed){
		this.speed = speed;
	}
}

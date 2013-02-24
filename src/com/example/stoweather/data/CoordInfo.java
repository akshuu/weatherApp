
package com.example.stoweather.data;

import java.io.Serializable;

public class CoordInfo implements Serializable{
	
	
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   	private float lat;
   	private float lon;

 	public float getLat(){
		return this.lat;
	}
	public void setLat(float lat){
		this.lat = lat;
	}
 	public float getLon(){
		return this.lon;
	}
	public void setLon(float lon){
		this.lon = lon;
	}
}


package com.example.stoweather.data;

import java.io.Serializable;

public class CloudInfo implements Serializable{
	
	
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   	private float all;

 	public float getAll(){
		return this.all;
	}
	public void setAll(float all){
		this.all = all;
	}
}

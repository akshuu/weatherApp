/**
 * 
 */
package com.example.stoweather.utils;


/**
 * @author akshatj
 *
 */
public class Constants {
	public static final int MIN_DISTANCE_FOR_GEO_UPDATES = 0; //Specified in meters	// 10Kms
	public static final int MIN_TIME_FOR_GEO_UPDATES = 30*60*1000; //Specified in milliseconds
	public static final String LOG_TAG = "StoWeather"; 
	public static final int AREA_RADIUS_KM = 10;
	
	public static final String TAG_NAME = "name";
	public static final String TAG_TEMP = "temp";
	public static final String TAG_STATUS = "status";
	public static final String TAG_ICON = "icon";
	
	public enum DAYS {SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THRUSDAY,FRIDAY,SATURDAY}	
	
	//Preferences Keys
	public static final String KEY_PREF_TEMPERATURE = "pref_temp";
	public static final String KEY_RADIUS_AREA_VALUE = "radius_area_value";

	
	public static final String WEATHER_CITY_URL = "http://api.openweathermap.org/data/2.1/find/city?lat=%f&lon=%f&radius=%d"; 
}



/**
 * 
 */
package com.example.stoweather.tasks;

import java.util.List;

import org.json.JSONObject;

import com.example.stoweather.data.CityWeather;
import com.example.stoweather.data.WeatherData;
import com.example.stoweather.utils.Constants;
import com.example.stoweather.utils.Helper;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author akshatj
 *
 */
public class WeatherDataTask extends AsyncTask<Double, Void, List<CityWeather>> {

	@Override
	protected List<CityWeather> doInBackground(Double... params) {
		WeatherData data = new WeatherData();
		double lat = params[0];
		double longitude = params[1];
		double radius = params[2];
		String cityURL = String.format(Constants.WEATHER_CITY_URL, lat,longitude,(int)radius);
    	Log.d(Constants.LOG_TAG, "URL == " + cityURL);
    	String strJson = null;
    	try {
			strJson = Helper.downloadFile(cityURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	List<CityWeather> cityList = null;
    	if(strJson != null){
    		Log.d(Constants.LOG_TAG, "JSON String == " + strJson.toString());
    		cityList = Helper.parseWeatherJSON(strJson);
    	}
    	else
    		Log.d(Constants.LOG_TAG, "no JSON data returned from server == ");
    	
		return cityList;
	}
	
	@Override
	protected void onPostExecute(List<CityWeather> result) {
		if(isCancelled()){
			result = null;
			return ;
		}
		super.onPostExecute(result);
	}

}

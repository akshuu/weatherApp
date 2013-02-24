package com.example.stoweather.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.NumberFormat;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.util.Log;

import com.example.stoweather.data.CityWeather;
import com.example.stoweather.data.WeatherData;
import com.google.gson.Gson;

public class Helper {

	   /**
     * Downloads files from URL. Use GZIP header to reduce network bandwidth
     * @param url
     * @return
     * @throws Exception
     */
    public static String downloadFile(String url) throws Exception{
    	
    	final HttpClient httpClient = AndroidHttpClient.newInstance("Android");
    	HttpResponse response;	
		HttpGet getMethod;
		Log.d(Constants.LOG_TAG, "URL == : " + url);
		URI uri = null;
		try {
			uri = new URI(url);
			getMethod = new HttpGet(uri);
		
		// Add GZIP request to allow for zipped response from server
			
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
			AndroidHttpClient.modifyRequestToAcceptGzipResponse(getMethod);

			response = httpClient.execute(getMethod);
			Log.d(Constants.LOG_TAG, "Http request status code is : " + String.valueOf(response.getStatusLine().getStatusCode()));
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.w(Constants.LOG_TAG, "Error " + response.getStatusLine().getStatusCode()
						+ " while retrieving data from " + url); 
				return null;
			}
		
			Header[] headers = response.getAllHeaders();
			boolean isGzipEncodedStream = false;
			for(Header head: headers){
				Log.v(Constants.LOG_TAG, "Response Http header name = " + head.getName() + ", value =" + head.getValue());
				if(head.getName().equals("Content-Encoding"))
					if(head.getValue().equalsIgnoreCase("gzip"))
						isGzipEncodedStream = true;
			}
			// Parse the input and construct the JSON
			final HttpEntity entity = response.getEntity();
			String json = "";
			if (entity != null) {
				InputStream inputStream = null;
				BufferedReader reader = null;
				StringBuilder buffer = new StringBuilder();

					try {
						if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO && isGzipEncodedStream){
							inputStream = AndroidHttpClient.getUngzippedContent(entity);
						}else
							inputStream = entity.getContent();
						
			             reader = new BufferedReader(new InputStreamReader(
			            		inputStream, "iso-8859-1"), 8);
			    		char[] tmp = new char[1024];
			    		int l;

			    		while ((l = reader.read(tmp)) != -1) {
			   				buffer.append(tmp, 0, l);
			   			}
			            Log.i(Constants.LOG_TAG, "JSON String == " + buffer.toString());
			            json = buffer.toString();
			        } catch (Exception e) {
			            Log.e(Constants.LOG_TAG, "Error converting result " + e.toString());
			        }finally{
			        	if(reader != null)
			        		reader.close();
			        	if(inputStream !=null)
			        		inputStream.close();
			        }
			 
			        // return JSON String
			        return json;
			}
		}
		catch(IllegalArgumentException exception) {
			Log.e(Constants.LOG_TAG, "The url " + uri + " is invalid! Can't proceed with the GET request");
			throw exception;
		}finally{
			((AndroidHttpClient)httpClient).close();																		
		}
		return null;
    }
    
    /**
     * Converts the temperature to celcius
     * @param temp
     * @return
     */
	public static float getTemperatureInCelsius(float temp) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(1);
		temp = temp - 273.15f;
		temp = Float.parseFloat(format.format(temp));
		return temp;
	}

	/**
	 * Converts the temperature to farhenhite
	 * @param temp
	 * @return
	 */
	public static  float getTemperatureInFarhenhite(float temp) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(1);
		temp = (temp - 273.15f)*1.8f + 32;
		temp = Float.parseFloat(format.format(temp));
		return temp ;
	}

	/**
	 * Parse the JSON string to weather object
	 * @param weather
	 * @return List of all cities weather info
	 */
    public static List<CityWeather> parseWeatherJSON(final String weather){
    	
    	Log.i(Constants.LOG_TAG,"Weather JSON String == " + weather);
   
    	Gson gson = new Gson();
    	WeatherData weatherData = (WeatherData)gson.fromJson(weather, WeatherData.class);
    	/*
    	JSONObject jsonObject = JSONObject.fromObject( weather );
    	Log.i(Constants.LOG_TAG,"Json obj == " + jsonObject);
    	
    	// Convert the json object to weather data
		WeatherData weatherData = (WeatherData) JSONObject.toBean( jsonObject, WeatherData.class );
		Log.i(Constants.LOG_TAG,"Total Cities == " + weatherData.getCnt());

    	// Morph the json object to related classes
		MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
		Morpher weatherMorpher = new BeanMorpher( CityWeather.class,  morpherRegistry);
		Morpher windMorpher = new BeanMorpher( WindInfo.class,  morpherRegistry);
		Morpher coordMorpher = new BeanMorpher( CoordInfo.class,  morpherRegistry);
		Morpher cloudMorpher = new BeanMorpher( CloudInfo.class,  morpherRegistry);
		Morpher mainMorpher = new BeanMorpher( Temperature.class,  morpherRegistry);
		morpherRegistry.registerMorpher( weatherMorpher );
		morpherRegistry.registerMorpher( windMorpher);
		morpherRegistry.registerMorpher( coordMorpher);
		morpherRegistry.registerMorpher( cloudMorpher);
		morpherRegistry.registerMorpher( mainMorpher);
		
		ObjectListMorpher wmorp = new ObjectListMorpher(weatherMorpher);
		@SuppressWarnings("unchecked")
		List<CityWeather> wList = (List<CityWeather>) wmorp.morph(weatherData.getList());
		*/
    	List<CityWeather> wList = (List<CityWeather>)weatherData.getList();
		for(CityWeather city: wList){
			Log.i(Constants.LOG_TAG,"City Name = " + city.getName());
			Log.i(Constants.LOG_TAG,"City Id = " + city.getId());
			Log.i(Constants.LOG_TAG,"City Could Info = " + city.getClouds().getAll());
			Log.i(Constants.LOG_TAG,"City Latitude = " + city.getCoord().getLat());
			Log.i(Constants.LOG_TAG,"City Longitude = " + city.getCoord().getLon());
		}
		
		return wList;
    }
}

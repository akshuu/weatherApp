package com.example.stoweather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stoweather.data.CityWeather;
import com.example.stoweather.tasks.WeatherDataTask;
import com.example.stoweather.utils.Constants;
import com.example.stoweather.utils.Helper;

public class MainActivity extends Activity {

	private Handler uiHandler;
	private Runnable getLocation;
	private GeoServiceManager mGeoServiceManager;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Start the location thread.
        uiHandler = new Handler(Looper.getMainLooper());
        getLocation  = new Runnable() {					
			@Override
			public void run() {
				mGeoServiceManager = new GeoServiceManager(getApplicationContext(),MainActivity.this);	
			}
		};
		uiHandler.post(getLocation);
		
		
    }

    WeatherDataTask weatherTask = null;
    ListView list;
    
    public void getWeatherData(double lat, double longitude){
    	weatherTask = new WeatherDataTask();
    	Double params[] = {lat,longitude};
    	List<CityWeather> cityList = null;
    	try {
			cityList = weatherTask.execute(params).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}finally{
			weatherTask = null;
		}

    	if(cityList != null){
    	/**
         * Updating parsed JSON data into ListView
         * */
    		List<HashMap<String, String>> cityAdapter = new ArrayList<HashMap<String, String>>();
    		for(CityWeather city:cityList){
    			HashMap<String, String> cityMap = new HashMap<String, String>();
    			cityMap.put(Constants.TAG_NAME, city.getName());
    			cityMap.put(Constants.TAG_TEMP, Helper.getTemperatureInCelsius(city.getTemperature().getTemp())+"");
    			cityMap.put(Constants.TAG_STATUS, city.getWeatherInfo().get(0).getDescription());
    			String icon = city.getWeatherInfo().get(0).getIcon();
    			icon = "i"+icon; 
    	        cityMap.put(Constants.TAG_ICON, icon);
    	        
    			cityAdapter.add(cityMap);
    		}
       
    		list=(ListView)findViewById(R.id.list);
    		ListAdapter adapter = new WeatherDataAdapter(this, cityAdapter);
    		
    		list.setAdapter(adapter);
    		
    		 // selecting single ListView item
            // Launching new screen on Selecting Single ListItem
    		list.setOnItemClickListener(new OnItemClickListener() {
     
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    // getting values from selected ListItem
                    String name = ((TextView) view.findViewById(R.id.cityname)).getText().toString();
                    String temperature = ((TextView) view.findViewById(R.id.temperature)).getText().toString();
//                    String description = ((TextView) view.findViewById(R.id.weatherState)).getText().toString();
     
                    // Starting new intent
                    //Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
                    //in.putExtra(TAG_NAME, name);
                    //in.putExtra(TAG_EMAIL, cost);
                    //in.putExtra(TAG_PHONE_MOBILE, description);
                    //startActivity(in);
                }
            });
    	}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	mGeoServiceManager.stop();
    	if(weatherTask != null)
    		weatherTask.cancel(true);
    	super.onPause();
    }
}

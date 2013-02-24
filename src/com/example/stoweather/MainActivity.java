package com.example.stoweather;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stoweather.data.CityWeather;
import com.example.stoweather.tasks.WeatherDataTask;
import com.example.stoweather.utils.Constants;

public class MainActivity extends Activity {

	private Handler uiHandler;
	private Runnable getLocation;
	private GeoServiceManager mGeoServiceManager;
	private WeatherDataTask weatherTask = null;
    private ListView list;

	
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
		PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
		
    }

    
    /**
     * Gets the current weather data based on users location.
     * @param lat
     * @param longitude
     */
    public void getWeatherData(double lat, double longitude){
    	weatherTask = new WeatherDataTask();
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	int radius = Constants.AREA_RADIUS_KM;
    	if(prefs.contains("radius_area_value"))
    		{
    		String sRadius = prefs.getString("radius_area_value", Constants.AREA_RADIUS_KM + "");
    		radius = Integer.parseInt(sRadius);
    		}
    	Double params[] = {lat,longitude,(double)radius};
    	
    	// Create async task to fetch weather info.
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
    	/*
         * Updating parsed JSON data into ListView
         */
    		List<CityWeather> cityAdapter = new ArrayList<CityWeather>(cityList);
       
    		list=(ListView)findViewById(R.id.list);
    		final ListAdapter adapter = new WeatherDataAdapter(this, cityAdapter);
    		
    		list.setAdapter(adapter);
    		
            // Launching new screen on Selecting Single ListItem
    		list.setOnItemClickListener(new OnItemClickListener() {
     
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    // Starting new intent
                    Intent in = new Intent(getApplicationContext(), CityWeatherList.class);
                    in.putExtra("City", (CityWeather)adapter.getItem(position));
                    startActivity(in);
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
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getTitle().equals(getString(R.string.menu_settings)))
    		updatePreferences();
    	else if((item.getTitle().equals(getString(R.string.menu_refresh)))){
    		refreshLocation();
    	}
    	return true;
    }
    
	/**
	 * Refresh location when asked by user
	 */
	void refreshLocation() {
		Log.i(Constants.LOG_TAG,"Refreshing the location...." );
		uiHandler.post(getLocation);
	}
	
	/**
	 * Show the preferences screen
	 */
    private void updatePreferences() {
    	 Intent settingsActivity = new Intent(getBaseContext(),
                 SettingsActivity.class);
         startActivity(settingsActivity);		
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    }
	@Override
    protected void onPause() {
		super.onPause();
    	mGeoServiceManager.stop();
    	if(weatherTask != null)
    		weatherTask.cancel(true);
    }
}

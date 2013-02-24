package com.example.stoweather;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.stoweather.data.CityWeather;
import com.example.stoweather.tasks.WeatherDataTask;
import com.example.stoweather.utils.Constants;

public class MainActivity extends Activity {

	private Handler uiHandler;
	private Runnable getLocation;
	private GeoServiceManager mGeoServiceManager;
	private WeatherDataTask weatherTask = null;
    private ListView list;
    private static final int UPDATE_UI = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
    }

    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	  // Start the location thread.
    	getLocation  = new Runnable() {					
			@Override
			public void run() {
				mGeoServiceManager = new GeoServiceManager(getApplicationContext(),MainActivity.this,uiHandler);	
			}
		};
		
	    // Handler for updating text fields on the UI like the lat/long and address.
		uiHandler = new Handler() {
          public void handleMessage(Message msg) {
              switch (msg.what) {
                  case UPDATE_UI:
                  	getWeatherData((Double[]) msg.obj);
                      break;
              }
          }
      };
		uiHandler.post(getLocation);
    }
    /**
     * Gets the current weather data based on users location.
     * @param lat
     * @param longitude
     */
    public void getWeatherData(Double[] coords){
    
    	weatherTask = new WeatherDataTask();
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	int radius = Constants.AREA_RADIUS_KM;
    	if(prefs.contains(Constants.KEY_RADIUS_AREA_VALUE))
    		{
    		String sRadius = prefs.getString(Constants.KEY_RADIUS_AREA_VALUE, Constants.AREA_RADIUS_KM + "");
    		radius = Integer.parseInt(sRadius);
    		}
    	Double params[] = {coords[0],coords[1],(double)radius};
    	
    	// Create async task to fetch weather info.
    	List<CityWeather> cityList = null;
    	try {
    		weatherTask.execute(params);
			cityList = weatherTask.get();
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

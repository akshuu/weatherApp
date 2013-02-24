package com.example.stoweather;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.stoweather.utils.Constants;

public class SettingsActivity extends PreferenceActivity
			implements OnSharedPreferenceChangeListener{
    
	public static final String KEY_PREF_TEMPERATURE = "pref_temp";
	public static final String KEY_RADIUS_AREA_VALUE = "radius_area_value";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.i(Constants.LOG_TAG,"Pref Radius key == " + prefs.contains("pref_radius"));
        Log.i(Constants.LOG_TAG,"Pref Temp key == " + prefs.contains(KEY_PREF_TEMPERATURE));
        Log.i(Constants.LOG_TAG,"Pref Temp key == " + prefs.getString(KEY_RADIUS_AREA_VALUE,"30"));
        Log.i(Constants.LOG_TAG,"Pref Temp key == " + prefs.getBoolean(KEY_PREF_TEMPERATURE,false));
    }

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		// register for change listeners
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		//Unregister for change listeners
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		 if(KEY_RADIUS_AREA_VALUE.equals(key)){
			 
		 }
	}
}
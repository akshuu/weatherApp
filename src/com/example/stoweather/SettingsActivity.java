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
    

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
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
		 if(Constants.KEY_RADIUS_AREA_VALUE.equals(key)){
			 
		 }
	}
}
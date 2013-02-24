/**
 * 
 */
package com.example.stoweather;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stoweather.data.CityWeather;
import com.example.stoweather.utils.Constants;
import com.example.stoweather.utils.Helper;

/**
 * @author akshatj
 *
 */
public class CityWeatherList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_info);
		Bundle bundle = getIntent().getExtras();
		CityWeather city = (CityWeather) bundle.get("City");
		Log.i(Constants.LOG_TAG, "City nam = " + city.getName());
		
		updateUI(city);
	}

	
	
	private void updateUI(CityWeather city) {
		TextView txtCity = (TextView) findViewById(R.id.txtCityTxt);
		TextView txtDate = (TextView) findViewById(R.id.txtDate);
		TextView txtUpdated = (TextView) findViewById(R.id.txtUpdatedTimeTxt);
		TextView txtTemp = (TextView) findViewById(R.id.txtTempTxt);
		TextView txtTempMin = (TextView) findViewById(R.id.txtTempLowTxt);
		TextView txtTempMax = (TextView) findViewById(R.id.txtTempHighTxt);
		TextView txtWeatherStatus = (TextView) findViewById(R.id.txtWeatherStatusTxt);
		TextView txtSpeed = (TextView) findViewById(R.id.txtWindSpeedTxt);
//		TextView txtDirection = (TextView) findViewById(R.id.txtWindDirTxt);
		
		Date d = new Date(System.currentTimeMillis());
		
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
		
		String dayStr = getDayOfWeek(day);
		txtDate.setText(dayStr);
		ImageView weatherimage = (ImageView) findViewById(R.id.imgWeather);
		
		txtCity.setText(city.getName());
		txtTemp.setText(Helper.getTemperatureInCelsius(city.getTemperature().getTemp()) +" " + getString(R.string.degCelcius));
		txtTemp.setTypeface(null, Typeface.BOLD);
		txtTempMin.setText(getString(R.string.txtLow) + " " + Helper.getTemperatureInCelsius(city.getTemperature().getTemp_min()) +" " + getString(R.string.degCelcius));
		txtTempMax.setText(getString(R.string.txtHigh) + " " +Helper.getTemperatureInCelsius(city.getTemperature().getTemp_max()) +" " + getString(R.string.degCelcius));
		txtWeatherStatus.setText(city.getWeatherInfo().get(0).getDescription());
        String icon = city.getWeatherInfo().get(0).getIcon();
		icon = "i"+icon; 
		int r = getResources().getIdentifier(icon,"drawable",getPackageName());
        weatherimage.setBackgroundResource(r);
        
        txtSpeed.setText(city.getWind().getSpeed()+" " + getString(R.string.speedmps));

	}
	
	private String getDayOfWeek(int day) {
		Constants.DAYS[] days = Constants.DAYS.values();
		String value = days[day].name();
		return value;
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}

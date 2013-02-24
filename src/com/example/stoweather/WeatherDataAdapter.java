/**
 * 
 */
package com.example.stoweather;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stoweather.data.CityWeather;
import com.example.stoweather.utils.Helper;

/**
 * @author akshatj
 *
 */
public class WeatherDataAdapter extends BaseAdapter {

	private List<CityWeather> cityAdapter;
	private Activity activity;
	private LayoutInflater inflater;
	
	 public WeatherDataAdapter(Activity a, List<CityWeather> data) {
	        activity = a;
	        cityAdapter=data;
		    inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }
	 
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return cityAdapter.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if(cityAdapter!= null)
			return cityAdapter.get(position);
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item_img, null);
 
        TextView cityname = (TextView)vi.findViewById(R.id.cityname); // title
        TextView temperature = (TextView)vi.findViewById(R.id.temperature); // artist name
        ImageView weatherimage= (ImageView)vi.findViewById(R.id.imgWeatherState); // thumb image
 
        CityWeather city = cityAdapter.get(position);
 
        // Setting all values in listview
        cityname.setText(city.getName());
        temperature.setText(Helper.getTemperatureInCelsius(city.getTemperature().getTemp()) +" " + activity.getString(R.string.degCelcius));
        String icon = city.getWeatherInfo().get(0).getIcon();
		icon = "i"+icon; 
		int r = activity.getResources().getIdentifier(icon,"drawable",activity.getPackageName());
        weatherimage.setBackgroundResource(r);
        return vi;
	}

}

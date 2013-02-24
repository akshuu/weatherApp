/**
 * 
 */
package com.example.stoweather;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stoweather.utils.Constants;

/**
 * @author akshatj
 *
 */
public class WeatherDataAdapter extends BaseAdapter {

	private List<HashMap<String, String>> cityAdapter;
	private Activity activity;
	private LayoutInflater inflater;
	
	 public WeatherDataAdapter(Activity a, List<HashMap<String, String>> data) {
	        activity = a;
	        cityAdapter=data;
		    inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//	        imageLoader=new ImageLoader(activity.getApplicationContext());
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
		// TODO Auto-generated method stub
		if(cityAdapter!= null)
			return cityAdapter.get(position);
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
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
 
        HashMap<String, String> city;
        city = cityAdapter.get(position);
 
        // Setting all values in listview
        cityname.setText(city.get(Constants.TAG_NAME));
        temperature.setText(city.get(Constants.TAG_TEMP) +" " + activity.getString(R.string.degCelcius));
        String icon = city.get(Constants.TAG_ICON);
        
        int r = activity.getResources().getIdentifier(icon,"drawable",activity.getPackageName());
        weatherimage.setBackgroundResource(r);
        return vi;
	}

}

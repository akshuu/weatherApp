package com.example.stoweather;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.stoweather.utils.Constants;

/**
 * Provides utlities for querying user's geo location using GPS/Network provider.
 * 
 * NOTE: This class should be instantiated only from a thread which has a Looper associated with it.
 *
 */
public class GeoServiceManager {

	private LocationManager mLocationManager = null;
	private Geocoder mGeoCoder = null;
	private Address mCurrentAddress = null;
	private Location mNotYetGeoDecodedLocation = null;
	
	private Activity mActivity;
	private Context mContext;
	private Handler mHandler;
	private static final int UPDATE_UI = 2;
	private Location mOldLocation = null;
	
	public GeoServiceManager(Context context,Activity mAct, Handler uiHandler) {
		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		mGeoCoder = new Geocoder(context);
		this.mContext = context;
		this.mActivity = mAct;
		mHandler = uiHandler;
		
		try {
			// Register the listener with the Location Manager to receive location updates
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constants.MIN_TIME_FOR_GEO_UPDATES, 
													Constants.MIN_DISTANCE_FOR_GEO_UPDATES, mLocationListener);
			boolean gpsEnabled = mLocationManager
					  .isProviderEnabled(LocationManager.GPS_PROVIDER);
			if(gpsEnabled)
				mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.MIN_TIME_FOR_GEO_UPDATES, 
					Constants.MIN_DISTANCE_FOR_GEO_UPDATES, mLocationListener);
		}
		catch(IllegalArgumentException exception) {
			Log.e(Constants.LOG_TAG, "GeoServiceManager: One of the parameters to requestLocationUpdates is wrong. Caught exception " + exception.getMessage());
		}
		catch(SecurityException exception) {
			Log.e(Constants.LOG_TAG, "GeoServiceManager: No suitable permission to check for the location service! Caught exception " + exception.getMessage());
		}
		catch(RuntimeException exception) {
			Log.e(Constants.LOG_TAG, "GeoServiceManager: Calling thread doesn't have a looper. Caught exception " + exception.getMessage());			
		}
	}

	public void stop(){
		if(mLocationManager != null)
			mLocationManager.removeUpdates(mLocationListener);
	}
	
	public void refresh(){
	}
	/**
	 * Geo decode the current not yet decoded location
	 */
	private void geoDecodeLocation() {
		if (mNotYetGeoDecodedLocation != null) {
			try {
				List<Address> address = mGeoCoder.getFromLocation(mNotYetGeoDecodedLocation.getLatitude(), mNotYetGeoDecodedLocation.getLongitude(), 1);
				
				if (address!= null && address.size() > 0) {
					Log.v(Constants.LOG_TAG, "GeoServiceManager: Updated the current address...");
					mCurrentAddress = address.get(0);
				}
				
				//Successfully decoded, set the place holder to null
				
				mNotYetGeoDecodedLocation = null;
			}
			catch (IllegalArgumentException exception) {
				Log.e(Constants.LOG_TAG, "GeoServiceManager: Latitude/Longitude provided is invalid!");
				exception.printStackTrace();
			}
			catch (IOException exception) {
				Log.e(Constants.LOG_TAG, "GeoServiceManager: Network error or some other I/O error while geo coding the location!");
				exception.printStackTrace();
			}
		}		
	}
	
	
	public Address getCurrentAddress() {
		geoDecodeLocation();
		return mCurrentAddress; 
	}
	
	public void updateGridView(){
	 if(mCurrentAddress != null){
		Toast.makeText(mActivity.getApplicationContext(), "Fetching weather information...", Toast.LENGTH_SHORT).show();
		 Message.obtain(mHandler,
	                UPDATE_UI,
	                new Double[]{mCurrentAddress.getLatitude() , mCurrentAddress.getLongitude()}).sendToTarget();
	 }
	}
	

	// Define a listener that responds to location updates
    LocationListener mLocationListener = new LocationListener() {
    	
    	// Called when a new location is found by the network location provider.
    	public void onLocationChanged(Location location) {
    		if(mOldLocation!= null)
    			if((location.getTime() - mOldLocation.getTime()) < 2*60*1000){		// Ignore minor updates less than 2 minutes
    				Log.i(Constants.LOG_TAG, "GeoServiceManager:ignoring this update " + location.getTime());
    				return;
    			}
    		Log.i(Constants.LOG_TAG, "GeoServiceManager: Received a location update!");
    		mOldLocation = location;
    		mNotYetGeoDecodedLocation = location;
    		geoDecodeLocation();
    		updateGridView();
    	}

    	public void onStatusChanged(String provider, int status, Bundle extras) {}

    	public void onProviderEnabled(String provider) {
    		Log.i(Constants.LOG_TAG, "GeoServiceManager: Provider had enabled location updates!");
    	}

    	public void onProviderDisabled(String provider) {
    		Log.i(Constants.LOG_TAG, "GeoServiceManager: Provider had disabled location updates! " + provider);
    		// Fix this ,as this gets call even if one of the provide is disabled
    		Toast.makeText(mContext, "Please enable Location services to use this App", Toast.LENGTH_LONG).show();
    	}
    };
}

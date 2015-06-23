package com.example.claremontantibiketheft;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPS extends Service implements LocationListener {
	
	//ALL THE VARIABLES ASSOCIATED WITH A GPS
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars
	
	//TODO why is this private and final? what is a context?
	private final Context mContext;

	boolean isGPSEnabled = false; //check if GPS is enabled
	boolean isNetworkEnabled = false; //check for network status
	boolean canGetLocation = false; //check if GPS can get location

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	// The minimum distance to change location in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	//TODO what does location manager do?
	protected LocationManager locationManager;

	//TODO what do contexts do?
	public GPS(Context context) {
		this.mContext = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			//TODO what does this do?
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.canGetLocation = true; //GPS can get location!
				if (isNetworkEnabled) { //check if network is available
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this); 
					//TODO what does requestLocationupdates do? should learn more about these parameters?
					
					Log.d("Network", "Network");
					if (locationManager != null) { 
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						//TODO what does getLastKnownLocation do?
						
						if (location != null) {
							latitude = location.getLatitude(); //sets latitude
							longitude = location.getLongitude(); //sets longitude
						}
					}
				}
				
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) { //check if GPS is enabled
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled"); 
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude(); //sets latitude
								longitude = location.getLongitude(); //sets longitude
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}
	
	/**
	 * Stop using GPS listener
	 * Calling this function will stop using GPS in your app
	 * */
	public void stopUsingGPS(){
		if(locationManager != null){ //TODO why check if locationManager is null or not?
			locationManager.removeUpdates(GPS.this); //TODO what does removeUpdates do?
		}		
	}
	
	/**
	 * Function to get latitude
	 * */
	public double getLatitude(){
		if(location != null){ //check if we know location
			latitude = location.getLatitude(); //set latitude to location's latitude
		}
		
		return latitude;
	}
	
	/**
	 * Function to get longitude
	 * */
	public double getLongitude(){
		if(location != null){ //check if we know location
			longitude = location.getLongitude(); //set longitude to location's longitude
		}
		
		return longitude;
	}
	
	public boolean canGetLocation() {
		return this.canGetLocation; //TODO what does this return statement do?
	}
	
	/**
	 * Function to show settings alert dialog
	 * On pressing Settings button will launch Settings Options
	 * */
	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
   	 	//TODO what does this do?
		
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) { //called when button is clicked
            	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            	mContext.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { //called when button is clicked
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
	}

	
	//NOT SURE WHY THESE ARE AUTO-GENERATED
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}

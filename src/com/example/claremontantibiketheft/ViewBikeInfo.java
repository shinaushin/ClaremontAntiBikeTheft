package com.example.claremontantibiketheft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class ViewBikeInfo extends FragmentActivity{
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars			
		
	public static final String PREFS_NAME = "MyPrefsFile"; //TODO what does this do?
	public SharedPreferences prefs = null; //TODO what does this do? 
	public SharedPreferences.Editor editor = null; //TODO what does this do?
	
	/**
	 * TODO what does this method do?
	 * param: TODO find out what a bundle does
	 */
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //TODO super to what?
		setContentView(R.layout.view_bike_info); //sets layout with view_bike_info
		
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TOOD what does this do?
		editor = prefs.edit(); //TODO what does this do?
		
		GridView grid = (GridView) findViewById(R.id.gridView); //TODO what's a GridView object?
		grid.setBackgroundColor(Color.LTGRAY); //sets background color of grid to light gray
		final GridBikeInfo ga = new GridBikeInfo(this); //TODO why do I need this?
	    grid.setAdapter(ga); //TODO what does setting the adapter do?
	    
	    String serial = prefs.getString("serial", ""); //retrieves the serial number
	    String descript = prefs.getString("description", ""); //retrieves the description of the bike
	    
	    TextView tv = (TextView) findViewById(R.id.serialNum); //creates a TextView with id serialNum
    	tv.setText("Serial Number: " + serial.substring(0,serial.length()-2)); //sets the textbox with the bike's serial number
    	//it has -2 because it has to subtract out the part that differentiates duplicates

    	tv = (TextView) findViewById(R.id.BikeDescript); //sets up another TextView with id BikeDescript
    	tv.setText("Description: " + descript); //sets the textbox with description of bike
	}
	
	/**
	 * method is called when button is pressed to go back to home page
	 * @param view
	 */
	public void backto1 (View view) { 
		Intent intent = new Intent(this, TheFiveFrags.class); //goes from current view to TheFiveFrags fragment
		startActivity(intent); //initiates the intent
	}
}

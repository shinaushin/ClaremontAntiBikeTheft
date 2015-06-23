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

public class FinishedReport extends FragmentActivity {
	
	//ALL THE VARIABLES ASSOCIATED WITH A FILLED-OUT REPORT
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars
	
	private int hour; //what hour bike was stolen
	private int minute; //what minute bike was stolen
 
	private int year; //what year bike was stolen
	private int month; //what month bike was stolen
	private int day; //what day bike was stolen
 
	//TODO need to find out what these two vars do
	static final int DATE_DIALOG_ID = 999;
	static final int TIME_DIALOG_ID = 998;
	
	public String reportNum = ""; //report number of incident report
	public String policeEmail = ""; //email address of police associated with incident
	public String didLock = ""; //whether the bike was locked or not when it was stolen
	
	public String lockBrand = ""; //brand of lock with the bike
	//TODO what if there was no lock with bike?
	
	public String bikeLoc = ""; //location of the bike when it was stolen
	public String moreDescript = ""; //extra description of bike
	
	//TODO i'll look more into how these function
	public static final String PREFS_NAME = "MyPrefsFile";
	public SharedPreferences prefs = null; 
	public SharedPreferences.Editor editor = null;
	
	/**
	 * TODO find out what the onCreate method does
	 * param savedInstanceState - find out how it functions
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO super to what?
		
		setContentView(R.layout.finished_report); 
		//sets the page that the user is looking at to the specified layout
		
		//TODO find out what these lines do
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
		
	
		GridView grid = (GridView) findViewById(R.id.theft_grid); //grid for pictures of theft site
		grid.setBackgroundColor(Color.LTGRAY); //sets background color of grid
		
		//TODO why final variable? why need instance of GridIncidentReport?
		final GridIncidentReport ga = new GridIncidentReport(this);
	    
		//TODO what does setAdapter do?
		grid.setAdapter(ga);
	    
	    reportNum = prefs.getString("reportNum", ""); //retrieves report number that is stored
	    policeEmail = prefs.getString("policeEmail", ""); //retrieves police email address that is stored
	    didLock = prefs.getString("didLock", ""); //retrieves whether or not bike was locked
	    
	    if (didLock.toLowerCase().equals("y")) { //if bike was locked
	    	didLock = "Yes"; //changes didLock to an actual word
	    } else {
	    	didLock = "No"; //changes didLock to an actual word and not just 'n'
	    }
	    
	    lockBrand = prefs.getString("lockBrand", ""); //retrieves brand of lock stored
    	bikeLoc = prefs.getString("bikeLoc", ""); //retrieves location of bike when stolen
    	moreDescript = prefs.getString("moreDescript", ""); //retrieves additional description on bike
    	hour = prefs.getInt("hour", 0); //retrieves hour when bike was stolen
    	minute = prefs.getInt("minute", 0); //retrieves minute when bike was stolen
    	year = prefs.getInt("year", 0); //retrieves year when bike was stolen
    	month = prefs.getInt("month", 0); //retrieves month when bike was stolen
    	day = prefs.getInt("day", 0); //retrieves day when bike was stolen
    	
    	//sets up textview with specified text and variable value
    	TextView tv = (TextView) findViewById(R.id.police_num);
    	tv.setText("Police Report Number: " + reportNum);

    	tv = (TextView) findViewById(R.id.police_email);
    	tv.setText("Police Email Address: " + policeEmail);
    	
    	tv = (TextView) findViewById(R.id.tvDate);
    	tv.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year).append(" "));
    	
    	tv = (TextView) findViewById(R.id.tvTime);
    	tv.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
    	
    	tv = (TextView) findViewById(R.id.locked);
    	tv.setText("Did you lock the bike? " + didLock);
    	
    	tv = (TextView) findViewById(R.id.lock_type);
    	tv.setText("Lock Brand: " + lockBrand);
    	
    	tv = (TextView) findViewById(R.id.bike_place);
    	tv.setText("Where Bike was Placed: " + bikeLoc);
    	
    	tv = (TextView) findViewById(R.id.description);
    	tv.setText(moreDescript);
	}
	
	//method to display time correctly
	private static String pad(int c) {
		if (c >= 10)
		    return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
			//ex: if c == 9, result --> 09
	}
	
	//redirects to five fragments
	public void onClick (View view) {
		//TODO should learn more on parameters of intent
		Intent intent = new Intent(this, TheFiveFrags.class);
		startActivity(intent);
	}
}


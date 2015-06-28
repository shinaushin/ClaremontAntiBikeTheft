package com.example.claremontantibiketheft;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;

public class UnfinishedReport extends FragmentActivity {
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars
	
	private TextView tvDisplayDate; //textview to display date
	private Button btnChangeDate; //button used to change the date
	private TextView tvDisplayTime; //textview used to display the time
	private Button btnChangeTime; //button used to change the time
	
	public FTPClient client = new FTPClient();
	public HashMap<String, User> users = new HashMap<String, User>(); //TODO hashmap of name and user objects
	public ArrayList<String> reports = new ArrayList<String>(); //arraylist of report objects
 
	private int hour; //var used to store the hour
	private int minute; //var used to store the minute
 
	private int year; //var used to store the year
	private int month; //var used to store the month
	private int day; //var used to store the day
	public Date currentD; //var used to store the current date
	public Date currentT; //var used to store the current time
 
	static final int DATE_DIALOG_ID = 999; //TODO what does this do?
	static final int TIME_DIALOG_ID = 998; //TODO what does this do?
	
	public String reportNum = ""; //string to store the report number
	public String policeEmail = ""; //string to store the police email address
	public String didLock = ""; //string to store whether or not the bike was locked
	public String lockBrand = ""; //string to store the brand of the lock
	public String bikeLoc = ""; //string to store the location of the bike when stolen
	public String moreDescript = ""; //string to store further description of the bike
	
	public static final String PREFS_NAME = "MyPrefsFile"; //TODO what does this do?
	public SharedPreferences prefs = null; //TODO what does this do?
	public SharedPreferences.Editor editor = null; //TODO what does this do?
	public long time = 0; //used to store time of when picture is taken
	
	/**
	 * creates layout for partially completed report and allows user to still edit report
	 * param - savedInstanceState: TODO what does this do?
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //TODO what does this super to?
		setContentView(R.layout.unfinished_report); //sets the content to this specified layout
		
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TODO what does this do?
		editor = prefs.edit(); //TODO what does this do?
		
		setDate(); //calls setDate method
		addListenerOnDate(); //adds a listener on the date button
		
		setTime(); //calls setTime method
		addListenerOnTime(); //adds a listener on the time button
		
		GridView grid = (GridView) findViewById(R.id.theft_grid); //sets grid to a GridView with specified ID
		grid.setBackgroundColor(Color.LTGRAY); //sets background color of the grid to light gray
		final GridIncidentReport ga = new GridIncidentReport(this); //TODO why do we need this?
	    grid.setAdapter(ga); //TODO what does setAdatper do?
	    
	    Button photoButton = (Button) this.findViewById(R.id.photo_button); //sets photoButton to a button with specified ID
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	time = System.currentTimeMillis();
                File f = new File(android.os.Environment.getExternalStorageDirectory(), time+".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);
            }
        }); //sets a listener on photoButton when user takes picture
	    
	    EditText editText = (EditText) findViewById(R.id.police_report); //sets editText ID to police report
		editText.setText(prefs.getString("reportNum", ""), TextView.BufferType.EDITABLE); //sets police report to what was stored in reportNum
		
		editText = (EditText) findViewById(R.id.police_email); //sets editText ID to police email
		editText.setText(prefs.getString("policeEmail", ""), TextView.BufferType.EDITABLE); //sets police email to what was stored in policeEmail
		
		editText = (EditText) findViewById(R.id.yes_no); //sets editText ID to didLock
		editText.setText(prefs.getString("didLock", ""), TextView.BufferType.EDITABLE); //sets didLock t to what was stored in didLock
		
		editText = (EditText) findViewById(R.id.lock_brand); //sets editText ID to lock brand
		editText.setText(prefs.getString("lockBrand", ""), TextView.BufferType.EDITABLE); //sets lock brand to what was stored in lockBrand
		
		editText = (EditText) findViewById(R.id.bike_place); //sets editText ID to bike place
		editText.setText(prefs.getString("bikeLoc", ""), TextView.BufferType.EDITABLE); //sets bike place to what was stored in bikeLoc
		
		editText = (EditText) findViewById(R.id.description); //sets editText ID to description
		editText.setText(prefs.getString("moreDescript", ""), TextView.BufferType.EDITABLE); //sets description to what was stored in moreDescript
	}
	
	/**
	 * TODO what does this do?
	 * param - requestCode: TODO what does this do?
	 * 		   resultCode: TODO what does this do?
	 * 		   data:  TODO what does this do?
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		if (resultCode == RESULT_OK) { //TODO why would resultCode equal RESULT_OK
            if (requestCode == 1) { //TODO why would requestCode = 1
                File f = new File(Environment.getExternalStorageDirectory().toString()); //directory to the external storage of the phone
                for (File temp : f.listFiles()) { //loop through all the files in the external storage directory
                    if (temp.getName().equals(time+".jpg")) {
                        f = temp;
                        HashSet<String> setter = (HashSet<String>) prefs.getStringSet("key2", new HashSet<String>()); //TODO what is key2?
                        setter.add(Uri.fromFile(f).toString().substring(7)); //adds File f to setter
                        editor.putStringSet("key2",  setter); //set key2 to setter
                        editor.commit(); //saves the edits
                        GridView grid = (GridView) findViewById(R.id.theft_grid); //sets grid to ID of theft_grid
                		grid.setBackgroundColor(Color.LTGRAY); //sets background color of that grid to light gray
                		GridIncidentReport ga = new GridIncidentReport(this); //TODO why do I need this?
                	    grid.setAdapter(ga); //TODO what does setAdapter do?
                        break;
                    }
                }
            }
		}
	}
	
	/**
	 * clears out all the pictures in the theft_grid
	 * @param view - TODO what does this do?
	 */
	public void restart(View view) {
		editor.putStringSet("key2",  null); //sets key2 to null
		editor.commit(); //saves edits
		GridView grid = (GridView) findViewById(R.id.theft_grid); //sets a grid to ID of theft_grid
		grid.setBackgroundColor(Color.LTGRAY); //sets background color of grid to light gray
		GridIncidentReport ga = new GridIncidentReport(this); //TODO what does this do?
	    grid.setAdapter(ga); //TODO what does setAdapter do?
	}
	
	/**
	 *  display date
	 */
	public void setDate() {
		tvDisplayDate = (TextView) findViewById(R.id.tvDate); //sets tvDisplayDate to ID of tvDate
		currentD = new Date(); //currentD is set to current date at that moment
		year = prefs.getInt("year", 2014); //gets the year stored in the prefs
		month = prefs.getInt("month", 0); //gets the month stored in the prefs
		day = prefs.getInt("day", 1); //gets the day stored in the prefs
 
		tvDisplayDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append(" ")); // set date into textview
	}
 
	/**
	 * adds listener for date picker
	 */
	public void addListenerOnDate() {
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate); //sets btnChangeDate to ID of btnChangeDtae
		
		btnChangeDate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		}); //sets a click listener to the change date button
	}
 
	/**
	 * dialog for date picker and time picker
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		   
		case TIME_DIALOG_ID: 
			// set time picker
			return new TimePickerDialog(this, 
                                       timePickerListener, hour, minute,false);
		}
		
		return null;
	}
 
	//listener for date picker
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
 
		/** 
		 * when dialog box is closed, below method will be called.
		 * param - view: TODO what does this do?
		 * 		   selectedYear: year selected by user
		 * 		   selectedMonth: month selected by user
		 * 		   selectedDay: day selected by user
		 */
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			tvDisplayDate.setText(new StringBuilder().append(month + 1)
			   .append("-").append(day).append("-").append(year)
			   .append(" ")); // set selected date into textview
		}
	};
	
	/**
	 * display time
	 */
	public void setTime() {
		tvDisplayTime = (TextView) findViewById(R.id.tvTime); //sets tvDisplayTime to ID of tvTime
		currentT = new Date(); //sets currentT to current time
		hour = prefs.getInt("hour", 0); //sets hour to whatever was stored in hour in prefs
		minute = prefs.getInt("minute", 0); //sets minute to whatever was stored in minute in prefs
 
		// set time into textview
		tvDisplayTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
	}
 
	public void addListenerOnTime() {
		btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
 
		btnChangeTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		}); //adds listener to time picker
	}
 
	//listener for time picker
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		/**
		 * param - view: TODO what does this do?
		 * 		   selectedHour: hour selected by the user
		 * 		   selectedMinute: minute selected by the user
		 */
		public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			tvDisplayTime.setText(new StringBuilder().append(pad(hour))
					.append(":").append(pad(minute))); // set time into textview
		}
	};
 
	//method to help display time
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c); //ex: 9 --> 09
	}
	
	public void onClick (View view) {
		//TODO  check how logic works
	}
}

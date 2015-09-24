 package com.example.claremontantibiketheft;
   
import java.io.File;  
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;
 
public class NewReport extends FragmentActivity {
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars	
		
	private TextView tvDisplayDate; //textview for displaying the date
	private Button btnChangeDate; //button for changing the date
	private TextView tvDisplayTime; //textview for displaying the time
	private Button btnChangeTime; //button for changing the time
	public FTPClient client = new FTPClient();
	public HashMap<String, Bike> bikes = new HashMap<String, Bike>(); //hashmap of bike IDs and bike objects
	public HashMap<String, User> users = new HashMap<String, User>(); //hashmap of user names and User objects
	public ArrayList<String> reports = new ArrayList<String>(); //arraylist of report IDs

	private Date currentD = new Date(); //date obj for current date
	private Date currentT = new Date(); //date obj for current time
	
	private int hour = currentD.getHours(); //initialize the hour to 0
	private int minute = currentD.getMinutes(); //initialize the minute to 0
 
	private int year = currentD.getYear()+1900; //var for year
	private int month = currentD.getMonth(); //var for month
	private int day = currentD.getDate(); //var for day
	
	static final int DATE_DIALOG_ID = 999; //TODO what does this dialog id do?
	static final int TIME_DIALOG_ID = 998; //TODO what does this dialog id do?
	
	public String reportNum = ""; //var for report number
	public String policeEmail = ""; //var for police email address
	public String didLock = ""; //var for whether or not bike was locked
	public String lockBrand = ""; //var for lock brand
	public String bikeLoc = ""; //var for location of bike when it was stolen
	public String moreDescript = ""; //var for additional description of bike
	
	public static final String PREFS_NAME = "MyPrefsFile"; //TODO what does this do?
	public SharedPreferences prefs = null; //TODO what does this do?
	public SharedPreferences.Editor editor = null; //TODO what does this do?
	public long time = 0; //keeps track of what time photo was taken
	
	/**
	 * creates layout for new report
	 * param - savedInstanceState: TODO not sure what it does
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //TODO what does this super to?
		setContentView(R.layout.new_report); //sets content to specified layout
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TODO what does this do?
		editor = prefs.edit(); //TODO what does this do?
		
		setDate(); //call method to set date
		addListenerOnDate(); //adds listener on button to change date
		
		setTime(); //calls method to set time
		addListenerOnTime(); //adds listener on button to change time
		
		GridView grid = (GridView) findViewById(R.id.theft_grid); //established a grid to display photos of stolen loc/bike
		grid.setBackgroundColor(Color.LTGRAY); //sets background color of grid to light gray
		final GridIncidentReport ga = new GridIncidentReport(this); //TODO why is this final? why do I need instance of GridIncidentReport?
	    grid.setAdapter(ga); //TODO what does setAdapter do?
	    
	    Button photoButton = (Button) this.findViewById(R.id.photo_button); //button to take photo
        photoButton.setOnClickListener(new View.OnClickListener() { //adds listener on button

            @Override
            public void onClick(View v) { //method for when picture is taken
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	time = System.currentTimeMillis(); //keeps track of what time picture was taken
                File f = new File(android.os.Environment.getExternalStorageDirectory(), time+".jpg"); //creates a file object
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f)); //TODO what does this do?
                startActivityForResult(intent, 1); //TODO what does this do?
            }
        });
	}
	
	/**
	 * TODO not sure what this method does
	 * param - requestCode: TODO what does this do?
	 * 		   resultCode: TODO what does this do?
	 * 		   data: TODO what does this do?
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		if (resultCode == RESULT_OK) { //TODO why is/is not resultCode RESULT_OK?
            if (requestCode == 1) { //TODO why is request 1 or not?
                File f = new File(Environment.getExternalStorageDirectory().toString()); //TODO not exactly sure how this works
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals(time+".jpg")) { //TODO why does this condition exist?
                        f = temp;
                        HashSet<String> setter = (HashSet<String>) prefs.getStringSet("key2", new HashSet<String>()); //TODO what does this do?
                        setter.add(Uri.fromFile(f).toString().substring(7)); //TODO what does this do?
                        editor.putStringSet("key2",  setter); //stores setter into key2
                        editor.commit();
                        GridView grid = (GridView) findViewById(R.id.theft_grid); //creates a GridView object for pictures of incident report
                		grid.setBackgroundColor(Color.LTGRAY); //set background color of grid to light gray
                		GridIncidentReport ga = new GridIncidentReport(this); //TODO why do we need an instance of GridIncidentReport
                	    grid.setAdapter(ga); //TODO what does setAdapter do?
                        break;
                    }
                }
            }
		}
	}
	
	/**
	 * resets the picture grid to no pictures
	 * @param view - TODO not sure what this does
	 */
	public void restart(View view) {
		editor.putStringSet("key2",  null); //sets key2 to null
		editor.commit();
		GridView grid = (GridView) findViewById(R.id.theft_grid); //sets GridView object to specified layout
		grid.setBackgroundColor(Color.LTGRAY); //sets background color of gridview to light gray
		GridIncidentReport ga = new GridIncidentReport(this); //TODO why do we need this GridIncidentReport object?
	    grid.setAdapter(ga); //TODO what does setAdapter do?
	}
	
	/**
	 * display date
	 */
	public void setDate() {
 
		tvDisplayDate = (TextView) findViewById(R.id.tvDate); //assigns tvDisplayDate to textview with specified ID
		currentD = new Date(); //currentD is assigned to the current date
		
		editor.putInt("year", currentD.getYear()+1900);
		editor.putInt("month",  currentD.getMonth());
		editor.putInt("day",  currentD.getDate());
		editor.commit();
		
		year = prefs.getInt("year", currentD.getYear()+1900); //year is assigned to value stored in "year"
		month = prefs.getInt("month", currentD.getMonth()); //month is assigned to the value stored in "month"
		day = prefs.getInt("day", currentD.getDate()); //day is assignd to the value stored in "day"
 
		// set date
		tvDisplayDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append(" "));
	}
 
	/**
	 * listener for date
	 */
	public void addListenerOnDate() {
 
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate); //btnChangeDate assigned to button with specified ID
 
		btnChangeDate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		}); //sets a listener for when the button is clicked
	}
 
	/**
	 * dialog for date picker and time picker
	 * param - ID: TODO what does this do?
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) { //TODO what does this switch case represent?
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
 
	/**
	 * listener for date picker
	 */
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		// when dialog box is closed, below method will be called.
		/**
		 * param - view: TODO what does this do?
		 * 		   selectedYear, selectedMonth, selectedDay: the values selected by the user
		 */
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
 
			// set selected date into textview
			tvDisplayDate.setText(new StringBuilder().append(month + 1)
			   .append("-").append(day).append("-").append(year)
			   .append(" "));
		}
	};
	
	/**
	 * display time
	 */
	public void setTime() {
		tvDisplayTime = (TextView) findViewById(R.id.tvTime); //assigned tvDisplayTime to the textview with specified ID
		currentT = new Date(); //currentT is assigned to new Date object, which is current date/time
		
		editor.putInt("hour", currentT.getHours()); //sets "hour" to 0
		editor.putInt("minute", currentT.getMinutes()); //sets "minute" to 0
		editor.commit(); //save edits
		
		hour = prefs.getInt("hour", currentT.getHours()); //hour is assigned to "hour"
		minute = prefs.getInt("minute", currentT.getMinutes()); //minute is assigned to "minute"
 
		// set time into textview
		tvDisplayTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
	}
 
	//listener on time picker
	public void addListenerOnTime() {
		btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
 
		btnChangeTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
	}
 
	//listener for time picker
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		/**
		 * param - view: TODO what does this do?
		 * 		   selectedHour, selectedMinute: values that the user has selected
		 */
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
 
			// set time into textview
			tvDisplayTime.setText(new StringBuilder().append(pad(hour))
					.append(":").append(pad(minute)));
		}
	};
 
	/**
	 * method to help display time correctly
	 * @param c - some integer
	 * @return
	 */
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c); //converts number to string
		else
		   return "0" + String.valueOf(c); //ex: 9 --> 09
	}
	
	/**
	 * 
	 * @param view - TODO what does this do?
	 */
	public void onClick (View view) {
		EditText editText = (EditText) findViewById(R.id.police_report); //assigns editText to text view of police report
    	reportNum = editText.getText().toString(); //reportNum is assigned to editText
    	
    	editText = (EditText) findViewById(R.id.police_email); //assigns editText to text view of police email addrss
    	policeEmail = editText.getText().toString(); //policeEmail is assigned to editText
    	
    	editText = (EditText) findViewById(R.id.yes_no); //assigns editText to text view of whether or not bike was locked
    	didLock = editText.getText().toString(); //didLock is assigned to editText
    	
    	editText = (EditText) findViewById(R.id.lock_brand); //assigns editText to text view of lock brand
    	lockBrand = editText.getText().toString(); //lockBrand is assigned to editText
    	
    	editText = (EditText) findViewById(R.id.bike_place); //assigns editText to text view of location of where bike was stolen
    	bikeLoc = editText.getText().toString(); //bikeLoc is assigned to editText
    	
    	editText = (EditText) findViewById(R.id.description); //assigns editText to text view of description of stolen bike
    	moreDescript = editText.getText().toString(); //moreDescript is assigned to editText
    	
    	String second = ""; //string to indicate everything that hasn't been completed yet
    	
    	//TODO needs a check for no duplicate police report number
		
		if (reportNum.equals("")) { //if no report number was added
			second += "police report number, "; //adds in warning of the fact that police report number is not filled in
		}
		
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //regex pattern to verify email address
		Pattern ePattern = Pattern.compile(EMAIL_PATTERN); //TODO what does this do?
		Matcher eMatch = ePattern.matcher(policeEmail); //TODO what does this do?
		
		if (!eMatch.matches()) //if the policeEmail is not an actual email address
		{
			second += "police email address, "; //adds in a warning about the fact that a police email address was not put in
		}
		
		if (!didLock.toUpperCase().equals("Y") && !didLock.toUpperCase().equals("N")) { //if there is no indication of whether or not bike was locked
			second += "Y/N question about whether or not bike was locked, "; //add in warning about the fact that user did not indicate whether or not bike was locked
		}
		
		if (lockBrand.equals("")) { //if no lock brand was put in
			second += "lock brand, "; //add in warning about the fact that lock brand is not put in
		}
		
		if (bikeLoc.equals("")) { //if the user did not put in the location of the stolen bike
			second += "location of bike when stolen, "; //adds in warning about how stolen bike location was not put in
		}
		
		String b = ""; //another string for warnings about what hasn't been filled in properly
		if (year > currentD.getYear()+1900) { //TODO why is this added with 1900?
			b = "wrong date, "; //adds in warning about how the wrong date is indicated
		}
		
		if (year == currentD.getYear()+1900 && month > currentD.getMonth()) { //TODO why is this added with 1900?
			b = "wrong date, "; //adds in warning about how the wrong date is indicated
		}
		
		if (year == currentD.getYear()+1900 && month == currentD.getMonth() && day > currentD.getDate()) { //TODO why is this added with 1900?
			b = "wrong date, "; //adds in warning about how the wrong date is indicated
		}
		
		if (year == currentD.getYear()+1900 && month == currentD.getMonth() && day == currentD.getDay() && hour > currentT.getHours()) { //TODO why is this added with 1900?
			b = "wrong time, "; //adds in warning about how the wrong date is indicated
		}
		
		if (year == currentD.getYear()+1900 && month == currentD.getMonth() && day == currentD.getDay() && hour == currentT.getHours() && minute > currentT.getMinutes()) { //TODO why is this added with 1900?
			b = "wrong time, "; //adds in warning about how the wrong date is indicated
		}
		
		second += b; //adds the two parts of the warnings together
		
		if (GridIncidentReport.str.size() == 0) { //if there are no pictures of the hypothesized theft site
			second += "need at least 1 photo of theft site, "; //adds in warning about how there needs to be at least 1 photo of theft site
		}
		
		if (GridIncidentReport.str.size() > 6) { //if there are more than 6 photos of theft site
			second += "too many photos of theft site, "; //adds in warning about how there are too many pictures selected
		}
		
		if (second.length() != 0) {
			second = second.substring(0,second.length()-2);
		}
		
		editor.putString("reportNum", reportNum); //stores police report number
		editor.putString("policeEmail", policeEmail); //stores police email address
		editor.putString("didLock", didLock); //stores whether or not stolen bike was locked
		editor.putString("lockBrand",  lockBrand); //stores the brand of the lock
		editor.putString("bikeLoc", bikeLoc); //stores the hypothesized location of where the bike was stolen
		editor.putString("moreDescript",  moreDescript); //stores additional description of the stolen bike
		editor.putInt("hour",  hour); //stores the hour of when the bike was stolen
		editor.putInt("minute", minute); //stores the minute of when the bike was stolen
		editor.putInt("year",  year); //stores the year of when the bike was stolen
		editor.putInt("month", month); //stores the month of when the bike was stolen
		editor.putInt("day",  day); //stores the day of when the bike was stolen
		editor.putBoolean("report",  true); // stores the fact that the report is opened
		editor.commit(); //saves edits

		if (!second.equals("")) { //if there are warnings about unfulfilled fields
			String popup = "It looks there are a few things unfinished:\n" + second;
			popup += "\nDo you still want to come back to this report later?";
			//this pops up when user tries to submit report after everything's been properly filled in
			
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        //user says that report has been completed accurately
			        case DialogInterface.BUTTON_POSITIVE:
			        	//TODO make sure logic works
			        	editor.putBoolean("reportFinish",  false);
			        	editor.commit();
			        	
			        	Intent intent = new Intent (NewReport.this, TheFiveFrags.class); //move from this class to TheFiveFrags
						startActivity(intent);
			            break;

			        //user wants to look over the report one more time
			        case DialogInterface.BUTTON_NEGATIVE:
			            break;
			        }
			    }
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(NewReport.this);
			builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show(); //builds the alert dialog for user to press yes or no
		} else {
			String popup = "It looks like you filled out everything. Are you sure everything is correct?"
					+ " We will be contacting law enforcement with the information on this report."; 
			//this pops up when user tries to submit report after everything's been properly filled in
			
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        //user says that report has been completed accurately
			        case DialogInterface.BUTTON_POSITIVE:
			        	//TODO make sure logic works
			        	editor.putBoolean("reportFinish",  true);
			        	editor.commit();
			            break;

			        //user wants to look over the report one more time
			        case DialogInterface.BUTTON_NEGATIVE:
			        	editor.putBoolean("reportFinish", false);
			        	editor.commit();
			            break;
			        }
			        Intent intent = new Intent (NewReport.this, TheFiveFrags.class); //moves from this class to TheFiveFrags
					startActivity(intent);
			    }
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(NewReport.this);
			builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show(); //builds the alert dialog for user to press yes or no
		}
	}
}
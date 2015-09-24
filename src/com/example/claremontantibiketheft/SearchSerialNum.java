package com.example.claremontantibiketheft;

import java.io.File; 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.commons.net.ftp.FTPClient;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.GridView;

public class SearchSerialNum extends FragmentActivity {
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars	
		
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
		setContentView(R.layout.search_serial_num); //sets content to specified layout
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TODO what does this do?
		editor = prefs.edit(); //TODO what does this do?
		
		GridView grid = (GridView) findViewById(R.id.gridview_serial); //established a grid to display photos of stolen loc/bike
		grid.setBackgroundColor(Color.LTGRAY); //sets background color of grid to light gray
		final SerialSearch ga = new SerialSearch(this); //TODO why is this final? why do I need instance of GridIncidentReport?
	    grid.setAdapter(ga); //TODO what does setAdapter do?
	    
	    Button photoButton = (Button) this.findViewById(R.id.take); //button to take photo
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
                        HashSet<String> setter = (HashSet<String>) prefs.getStringSet("key3", new HashSet<String>()); //TODO what does this do?
                        setter.add(Uri.fromFile(f).toString().substring(7)); //TODO what does this do?
                        editor.putStringSet("key3",  setter); //stores setter into key2
                        editor.commit();
                        GridView grid = (GridView) findViewById(R.id.gridview_serial); //creates a GridView object for pictures of incident report
                		grid.setBackgroundColor(Color.LTGRAY); //set background color of grid to light gray
                		SerialSearch ga = new SerialSearch(this); //TODO why do we need an instance of GridIncidentReport
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
	public void clear(View view) {
		editor.putStringSet("key3",  null); //sets key2 to null
		editor.commit();
		GridView grid = (GridView) findViewById(R.id.gridview_serial); //sets GridView object to specified layout
		grid.setBackgroundColor(Color.LTGRAY); //sets background color of gridview to light gray
		SerialSearch ga = new SerialSearch(this); //TODO why do we need this GridIncidentReport object?
	    grid.setAdapter(ga); //TODO what does setAdapter do?
	}
	
	/**
	 * 
	 * @param view - TODO what does this do?
	 */
	public void onClick (View view) {
		String second = ""; //string to indicate everything that hasn't been completed yet
    	
    	if (SerialSearch.str.size() == 0) { //if there are no pictures of the hypothesized theft site
			second += "need 1 photo of serial number, "; //adds in warning about how there needs to be at least 1 photo of theft site
		}
		
		if (SerialSearch.str.size() > 1) { //if there are more than 6 photos of theft site
			second += "too many photos of serial number, "; //adds in warning about how there are too many pictures selected
		}
		
		if (second.length() != 0) {
			second = second.substring(0,second.length()-2);
		}
		
		if (!second.equals("")) { //if there are warnings about unfulfilled fields
			AlertDialog alertDialog = new AlertDialog.Builder(SearchSerialNum.this).create();
			alertDialog.setTitle("Incomplete");
			alertDialog.setMessage("Please upload only one picture of the serial number.");
			alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
			    new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			            dialog.dismiss();
			        }
			    });
			alertDialog.show();
		} else {
			Intent intent = new Intent(SearchSerialNum.this, TheFiveFrags.class);
			startActivity(intent);
		}
	}
	
}

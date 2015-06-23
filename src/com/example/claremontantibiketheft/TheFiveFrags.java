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
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

public class TheFiveFrags extends FragmentActivity{
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars	
		
	SectionsPagerAdapter mSectionsPagerAdapter; //TODO what does this do?
	ViewPager mViewPager; //TODO what does this do?
	
	public FTPClient client = new FTPClient();
	public HashMap<String, Bike> bikes = new HashMap<String, Bike>(); //hashmap of serial ID number and bikes objets
	public HashMap<String, User> users = new HashMap<String, User>(); //hashmap of name and user objects
	public ArrayList<String> reports = new ArrayList<String>(); //arraylist of report ID numbers
	public static Button button; //button var
	public Bike bike = null; //bike var
	public String name = ""; //name of user
	public String streetAddress = ""; //street address of user
	public String emailAddress = ""; //email address of user
	public String phoneNum = ""; //phone number of user
	public String fb = ""; //fb url of user
	public String twitter = ""; //twitter url of user
	public String linkedin = ""; //linkedin url of user
	public String googlePlus = ""; //googleplus url of user
	
	public static final String PREFS_NAME = "MyPrefsFile"; //TODO what does this do?
	public SharedPreferences prefs = null; //TODO what does this do?
	public SharedPreferences.Editor editor = null; //TODO what does this do?
	
	public ArrayList<String> imageUrls = new ArrayList<String>(); //TODO what are urls?
	public Section2Fragment obj = new Section2Fragment(); //TODO what does this do?
	public HashSet<String> uri = new HashSet<String>(); //TODO what do uris do?
	
	public String server = "bikenab.com";
    int port = 21;
    String user = "bikenab";
    String pass = "Rufas123";
	
	/**
	 * TODO why is this premade? what does this do?
	 * param - menu: TODO what does this do?
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu); //TODO what does inflate do?
		return true;
	}
	
	/**
	 * why is this premade? what does this do?
	 * why is this an inner class?
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		/**
		 * TODO what does this do?
		 * @param fm - TODO what does a FragmentManager do?
		 */
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm); //TODO super to what?
		}

		/**
		 * gets the item of which page to go to
		 * param - position: index of which page is displayed
		 */
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null; //fragment var
			Bundle args = null; //TODO what does a bundle do?
			
			if (position == 0) {
				fragment = new TitleFragment();
				args = new Bundle(); //TODO what does args do?
				args.putInt(TitleFragment.ARG_SECTION_NUMBER, position + 1); //TODO what does putInt do?
			} else if (position == 1) {
				fragment = new Section3Fragment();
				 args= new Bundle();			
				 args.putInt(Section3Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 2) {
				fragment = new Section4Fragment();
				args = new Bundle();
				args.putInt(Section4Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 3) {
				fragment = new Section1Fragment();
				args = new Bundle();
				args.putInt(Section1Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 4) {
				fragment = new Section2Fragment();
				args = new Bundle();
				args.putInt(Section2Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 5) {
				fragment = new Section5Fragment();
				args = new Bundle();
				args.putInt(Section5Fragment.ARG_SECTION_NUMBER, position + 1);
			}else if (position == 6) {
				fragment = new Section6Fragment();
				args = new Bundle();
				args.putInt(Section6Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 7) {
				fragment = new Section7Fragment();
				args = new Bundle();
				args.putInt(Section7Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 8) {
				fragment = new Section8Fragment();
				args = new Bundle();
				args.putInt(Section8Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 9) {
				fragment = new Section9Fragment();
				args = new Bundle();
				args.putInt(Section9Fragment.ARG_SECTION_NUMBER, position + 1);
			}
			
			fragment.setArguments(args); //TODO what does setArguments do?
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 10 total pages.
			return 10;
		}

		/**
		 * gets the page of the title
		 * param - position: index of which page is displayed
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault(); //TODO what does locale do?
			switch (position) {
			case 0:
				return getString(R.string.title).toUpperCase(l);
			case 1:
				return getString(R.string.title_section3).toUpperCase(l);
			case 2:
				return getString(R.string.title_section4).toUpperCase(l);
			case 3:
				return getString(R.string.title_section1).toUpperCase(l);
			case 4:
				return getString(R.string.title_section2).toUpperCase(l);
			case 5:
				return "BIKESHOP";
			case 6:
				return "PAWNSHOP";
			case 7:
				return "LAW ENFORCEMENT";
			case 8:
				return "CAMPUS/SECURITY";
			case 9:
				return "FUNDRAISING";
			}
			return null;
		}
	}
	
	/**
	 * displays the contact info to fill when the app is opened for the first time
	 * or goes to fragment 1 when app is opened after the first time
	 * param - savedInstanceState: TODO what does this do?
	 */
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //TODO what does this super do?
		
		boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true); //boolean value for whether this is the first time the app is opened
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TODO what does this do?
		editor = prefs.edit(); //TODO what does this do?
		
		if (firstrun){ //if it is the first time the app is opened
	    	setContentView(R.layout.personal_info); //open the personal info layout
	    	editor.putBoolean("check", false); //TODO what does check do?
	    	editor.putBoolean("report", false); //no report has been filled out yet
			editor.commit(); //save edits
		} else {
			setContentView(R.layout.activity_main); //open the layout of the main page
			
			prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TODO what does this do?
			editor = prefs.edit(); //TODO what does this do?
			 
			mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager()); //TODO what does this do?
	
			mViewPager = (ViewPager) findViewById(R.id.pager); //TODO what does this do?
			mViewPager.setAdapter(mSectionsPagerAdapter); //TODO what does this do?
		}
	}
	
	/**
	 * submit contact info and verify everything is filled out correctly
	 * @param view - TODO what does this do?
	 */
	public void onClick(View view) {
		EditText editText = (EditText) findViewById(R.id.name); //sets editText to ID of name
    	name = editText.getText().toString()+"_0"; //stores user's name and a number to detect duplicates
    	
    	editText = (EditText) findViewById(R.id.street_address); //sets editText to ID of street address
    	streetAddress = editText.getText().toString(); //stores user's street address
    	
    	editText = (EditText) findViewById(R.id.email_address); //sets editText to ID of email address
    	emailAddress = editText.getText().toString(); //stores user's email address
    	
    	editText = (EditText) findViewById(R.id.phone_number); //sets editText to ID of phone number
    	phoneNum = editText.getText().toString(); //stores user's phone number
    	
    	editText = (EditText) findViewById(R.id.facebook); //sets editText to ID of facebook
    	fb = editText.getText().toString(); //stores user's facebook url
    	
    	editText = (EditText) findViewById(R.id.twitter); //sets editText to ID of twitter
    	twitter = editText.getText().toString(); //stores user's twitter url
    	
    	editText = (EditText) findViewById(R.id.google_plus); //sets editText to ID of google plus
    	googlePlus = editText.getText().toString(); //stores user's google plus url
    	
    	String first = "The following items have not been completed properly: "; //beginning of warning
		String second = ""; //another part of the warning string
		String message = ""; //total warning string
		
		if (name.equals("")) { //if name is not filled in
			second += "name, "; //add name to the warning
		}
		
		if (streetAddress.equals("")) { //if street address is not filled out
			second += "street address, "; //add street address to the warning
		}
	
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //regex pattern
		Pattern ePattern = Pattern.compile(EMAIL_PATTERN); //TODO what does a pattern do? what does compile do?
		Matcher eMatch = ePattern.matcher(emailAddress); //TODO what does a Matcher do? what does matcher do?
		
		if (!eMatch.matches()) //TODO what does this condition mean?
		{
			second += "email address, "; //add email address to warning
		}
		
		String expression = "(\\d{3}-){1,2}\\d{4}"; //another regex expression
	    CharSequence inputStr = phoneNum; //TODO what does a CharSequence do?
	    Pattern pattern = Pattern.compile(expression); //TODO what does a Pattern do? what does compile do?
	    Matcher matcher = pattern.matcher(inputStr); //TODO what does a Matcher do? what does matches do?
	    
	    if(!matcher.matches()){ //TODO what does this condition do?
	    	second += "phone number, "; //add phone number to warning
	    }
	    
	    //TODO validate street address, URLs (if possible)
	    
		message = first + second; //make a complete warning message
		
    	if (!second.equals("")) { //if there are some fields that aren't completed
    		new AlertDialog.Builder(this).setTitle("Not Completed").setMessage(message.substring(0,message.length()-2)).show(); //show an alert message
    	} else {
    		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstrun", false).commit(); //now firstRun is false
    		
    		editor.putString("name", name); //saves user's name
	    	editor.putString("streetAddress", streetAddress); //saves user's street address
	    	editor.putString("emailAddress", emailAddress); //saves user's email address
	    	editor.putString("phoneNum", phoneNum); //saves user's phone number
	    	editor.putString("fb", fb); //saves user's facebook url
	    	editor.putString("twitter", twitter); //saves user's twitter url
	    	editor.putString("googlePlus", googlePlus); //saves user's google plus url
			editor.commit(); //saves edits
			
			//SendUser obj = new SendUser();
			//obj.execute();
		 
    		Intent intent = new Intent(this, TheFiveFrags.class); //goes from this class to TheFiveFrags class
    		startActivity(intent);
    	}
	}
	
	/*public boolean ftpDownload(String srcFilePath, String desFilePath)
	{
	    boolean status = false;
	    try {
	        FileOutputStream desFileStream = new FileOutputStream(desFilePath);;
	        status = client.retrieveFile(srcFilePath, desFileStream);
	        desFileStream.flush();
	        desFileStream.close();

	        return status;
	    } catch (Exception e) {
	      
	    }

	    return status;
	}
	
	private class SendUser extends AsyncTask<Void, Void, Void> {
		 
		@Override
		protected Void doInBackground(Void... params) {
			try {
				client.connect("bikenab.com");
				client.enterLocalPassiveMode();
				boolean login = client.login("bikenab", "Rufas123");

			    if (login) {
			    	if (client.listFiles("users.ser").length == 1) { //do same for users.ser
			    		ftpDownload("/users.ser", "/storage/sdcard/users.ser");
			    		
			    		//deserialize file
			    		try
			            {
			                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/sdcard/users.ser"));
			                users = (HashMap) ois.readObject();
			            } catch(Exception ex) {
			            	Log.v("Serialization Read Error : ",ex.getMessage());
			                ex.printStackTrace();
			            }
			    		
			    		//edit hashmap
			    		has(name);
			    		User user = new User(name, streetAddress, emailAddress, phoneNum, fb, twitter, googlePlus);
			    		users.put(name, user);
			    		
			    		//serialize file
			    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/storage/sdcard/users.ser"))); //Select where you wish to save the file...
			            oos.writeObject(users); // write the class as an 'object'
			            oos.flush();
			    		
			    		//send file to server
		                client.enterLocalPassiveMode(); // important!
		                client.setFileType(FTP.BINARY_FILE_TYPE);
		                String data = "/storage/sdcard/users.ser";

		                FileInputStream in = new FileInputStream(new File(data));
		                boolean result = client.storeFile("/users.ser", in);
		                if (result) Log.v("upload result", "succeeded");
			    	} else {
			    		try
			            {
			    			User user = new User(name, streetAddress, emailAddress, phoneNum, fb, twitter, googlePlus);
			    			users.put(name, user);
			    			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/storage/sdcard/users.ser"))); //Select where you wish to save the file...
			    			oos.writeObject(users); // write the class as an 'object'
			    			oos.flush(); // flush the stream to insure all of the information was written 
			               
			    			//send file to server
			    			client.enterLocalPassiveMode(); // important!
			    			client.setFileType(FTP.BINARY_FILE_TYPE);
			    			String data = "/storage/sdcard/users.ser";
	
			    			FileInputStream in = new FileInputStream(new File(data));
			    			boolean result = client.storeFile("/users.ser", in);
			    			if (result) Log.v("upload result", "succeeded");
			            } catch(Exception ex) {
			            	Log.v("Serialization Save Error : ",ex.getMessage());
			            	ex.printStackTrace();
			            }
			    	}
			    	
			    	client.logout();
	    			client.disconnect();
			    } else {
			      System.out.println("Login fail...");
			    }
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	} */
	
	/**
	 * recursive program to deal with duplicates
	 * @param nam - user's name
	 */
	public void has (String nam) {
		if (users.containsKey(nam)) { //if users hashmap contains nam string
			int a = Integer.parseInt(nam.substring(nam.length()-1)); //gets the number associated with that name
			a++; //increments it
			name = nam.substring(0,nam.length()-1)+a; //checks if that number already exists in the hashmap
			has(nam.substring(0,nam.length()-1)+a); //recurses
		}
	}
	
//------------------------------------------------------------------------------------------------//
	
	/**
	 * TODO what does this display?
	 * @author shinaushin
	 *
	 */
	public class TitleFragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";
		/**
		 * TODO what does this do?
		 * param - inflater: TODO what does a LayoutInflater do?
		 * 		   container: TODO what does a ViewGroup do?
		 * 		   savedInstanceState: TODO what does a Bundle do?
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.title_fragment, container, false); //TODO what does inflate do? what are its parameters?
			return rootView;
		}
	}

//-----------------------------------------------------------------------------------------------//
	
	/**
	 * displays fragment 1
	 * @author shinaushin
	 *
	 */
	public class Section1Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section1, container, false);
			return rootView;
		}
		
		/**
		 * TODO what does this method do?
		 * param - savedInstanceState: TODO what does a Bundle do?
		 */
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState); //TODO what does this super to?
		}
	}
	
	/**
	 * registers a bike
	 * @param view - TODO what does this View do?
	 */
	public void register(View view) {
		if (prefs.getBoolean("check",  true) == true) { //if check == true
			Toast.makeText(getApplicationContext(), "A bike is already registered. Please deregister first.", Toast.LENGTH_LONG).show(); //displays warning message
		} else {
			editor.putBoolean("check", true); //sets check to true
			editor.commit(); //saves edit
			Intent intent = new Intent (this, RegisterBike.class); //goes from this class to RegisterBike class
			startActivity(intent);
		}
	}
	
	/**
	 * deregister a bike
	 * @param view - TODO what does a View do?
	 */
	public void deregister(View view) {
		if (prefs.getBoolean("check", true) == false) { //if check is not true
			Toast.makeText(getApplicationContext(), "You have not registered a bike.", Toast.LENGTH_LONG).show(); //displays warning saying that a bike is not registered
		} else {
			String popup = "Are you sure you want to deregister bike? All info associated with bike will be deleted.";
			
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    /**
			     * method for whether or not user wants to deregister bike when clicked
			     */
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        //erases all info on bike
			        case DialogInterface.BUTTON_POSITIVE:
			        	//sends serial number to server to remove bike from hashmap
    					//RemoveBike obj = new RemoveBike();
    					//obj.execute();
    					
    					Toast.makeText(getApplicationContext(), "Bike has been deregistered.", Toast.LENGTH_LONG).show(); //lets user know that bike has been deregistered
			            break;

			        case DialogInterface.BUTTON_NEGATIVE: //user does not actually want to deregister the bike
			            break;
			        }
			    }
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show(); //builds the dialog for user to click on
		}
	}
	
	/**
	 * method to view a registered bike
	 * @param view
	 */
	public void view (View view) {
		if (prefs.getBoolean("check", false) == true) { //if check is true
			Intent intent = new Intent(this, ViewBikeInfo.class); //go from this class to ViewBikeInfo class
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "You have not registered a bike.", Toast.LENGTH_LONG).show(); //displays message saying that bike has not been registered
		}
	}

	/*private class RemoveBike extends AsyncTask<Void, Void, Void> {
		 
		@Override
		protected Void doInBackground(Void... params) {
			try {
				client.connect("bikenab.com");
				client.enterLocalPassiveMode();
				boolean login = client.login("bikenab", "Rufas123");

			    if (login) {
			    	if (client.listFiles("bikes.ser").length == 1) {
			    		ftpDownload("/bikes.ser", "/storage/sdcard/bikes.ser");
			    		
			    		//deserialize file
			    		try
			            {
			                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/sdcard/bikes.ser"));
			                bikes = (HashMap) ois.readObject();
			            } catch(Exception ex) {
			            	
			            }
			    		
			    		bikes.remove(prefs.getString("serial", ""));
			    		//need to remove bike pictures also
			    		FTPFile[] files = client.listFiles("/Bike_Pictures/"+prefs.getString("serial","")+"/");
			    		
			    		for (int i = 2; i < files.length; i++) {
			    			client.deleteFile("/Bike_Pictures/"+prefs.getString("serial","")+"/"+files[i].getName());
			    		}
			    		boolean res = client.removeDirectory("/Bike_Pictures/"+prefs.getString("serial",""));
			    		
			    		//serialize file
			    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/storage/sdcard/bikes.ser"))); //Select where you wish to save the file...
			            oos.writeObject(bikes); // write the class as an 'object'
			            oos.flush();
			    		
			    		//send file to server
		                client.enterLocalPassiveMode(); // important!
		                client.setFileType(FTP.BINARY_FILE_TYPE);
		                String data = "/storage/sdcard/bikes.ser";

		                FileInputStream in = new FileInputStream(new File(data));
		                boolean result = client.storeFile("/bikes.ser", in);
		                if (result) Log.v("upload result", "succeeded");
		                
		                editor.putString("description", "");
						editor.putString("serial", "");
						editor.putStringSet("key", new HashSet<String>());
						editor.putBoolean("check",  false);
						editor.putStringSet("key",  new HashSet<String>());
						editor.commit(); 
			    	}
			    	
			    	client.logout();
	                client.disconnect();
			    }
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}*/
	
//------------------------------------------------------------------------------------------------//
	
	public class Section2Fragment extends Fragment {
		
		protected ImageLoader imageLoader = ImageLoader.getInstance(); //TODO what does an imageLoader do?
		
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		/**
		 * sets up fragment for user to put in bike info and submit
		 */
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}
		
		//premade
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section2, container, false);
			return rootView;
		}
	}
	
	/**
	 * method to create new report
	 * @param view - TODO what does a view do?
	 */
	public void report(View view) {
		if (prefs.getBoolean("check",  true) == false) { //if check is false
			Toast.makeText(getApplicationContext(), "Cannot report. No bike registered.", Toast.LENGTH_LONG).show(); //displays message saying that no bike is registered
		} else if (prefs.getBoolean("report", false) == true) { //if a report has been started
			Toast.makeText(getApplicationContext(), "Another report is currently in progress.\nPlease cancel current report to make a new report.\nOr click 'Go To Current Report' to continue current one.", Toast.LENGTH_LONG).show();
			//displays to user that a report is already in progress
		} else { //if no report is in progress
			editor.putStringSet("key2", null); //set key2 to null
			editor.putString("reportNum", ""); //set reportNum to empty string
			editor.putString("policeEmail", ""); //set policeEmail to empty string
			editor.putString("didLock", ""); //set didLock to empty string
			editor.putString("lockBrand",  ""); //set lockBrand to empty string
			editor.putString("bikeLoc", ""); //set bikeLoc to empty string
			editor.putString("moreDescript",  ""); //set moreDescript to empty string
			editor.putInt("hour",  0); //set hour to 0
			editor.putInt("minute", 0); //set minute to 0
			editor.putInt("year",  2014); //set year to 2014
			editor.putInt("month", 0); //set month to 0
			editor.putInt("day",  1); //set day to 1
			editor.commit(); //save edits
			
			GPS gps = new GPS(getBaseContext()); //TODO what does getBaseContext do?

			// check if GPS enabled		
	        if(gps.canGetLocation()){
	        	
	        	float latitude = (float) gps.getLatitude(); //gets latitude of user's phone
	        	float longitude = (float) gps.getLongitude(); //gets longitude of user's phone
	        	
	        	editor.putFloat("lat", latitude); //stores latitude
	        	editor.putFloat("lat", longitude); //stores longitude
	        	editor.commit(); //saves edits
	        }else{
	        	// Ask user to enable GPS/network in settings
	        	gps.showSettingsAlert();
	        }  
	        
	        Intent intent = new Intent(this, NewReport.class); //go from this class to NewReport class
	        startActivity(intent);
		}
	}
	
	/**
	 * allows user to view current report
	 * @param view - TODO what does View do?
	 */
	public void viewReport (View view) {
		if (prefs.getBoolean("report",  true) == false) { //if there is no report in progress
			Toast.makeText(getApplicationContext(), "There is no report in progress.", Toast.LENGTH_LONG).show(); //tell user that there is no report to see
		} else { //if there is a report in progress
			if (prefs.getBoolean("reportFinish", false) == true) { //if the report is done
				Intent intent = new Intent(this, FinishedReport.class); //go from this class to FinishedReport class
				startActivity(intent);
			} else { //if report is not done
				Intent intent = new Intent(this, UnfinishedReport.class); //go from this class to UnfinishedReport class
				startActivity(intent);
			}
		}
	}
	
	/**
	 * cancel report
	 * @param view - TODO what does a View do?
	 */
	public void cancelReport (View view) {
		//final RemoveReport obj1 = new RemoveReport();
	
		if (prefs.getBoolean("report", true) == false) { //if there is no report
			Toast.makeText(getApplicationContext(), "There is no current report.", Toast.LENGTH_LONG).show(); //tell user there is no report to delete
		} else if (prefs.getBoolean("reportFinish", true) == false) { //if there is a report in progress and it is not finished
			String popup = "It looks like you haven't finished filling out the report.\n"; //tell user that the report is not finished yet
			popup += "Are you sure you want to cancel it?"; //ask user if they want to delete it
			
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    /**
			     * method for what happens when user clicks on dialog
			     * param - dialog: TODO what does a DialogInterface do?
			     * 		   which: TODO what does this do?
			     */
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        case DialogInterface.BUTTON_POSITIVE: //if they do want to cancel it
			        	Toast.makeText(getApplicationContext(), "Report cancelled.", Toast.LENGTH_LONG).show(); //tell user that it is cancelled
						//obj1.execute();
			        	break;
			        	
			        case DialogInterface.BUTTON_NEGATIVE: //if they don't want to cancel it, nothing happens
			        	break;
			        }
			    }
			};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(TheFiveFrags.this); //builds an alert dialog
			builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show(); //sets positive button to yes and negative button to no
			
		} else { //else if report is finished
			String popup = "Has your bike been recovered"; //ask if user's bike is recovered
			
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    /**
			     * method for when user indicates if their bike has been recovered or not
			     * param - dialog: TODO what does a DialogInterface do?
			     * 		   which: TODO what does this do?
			     */
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        case DialogInterface.BUTTON_POSITIVE: //if bike has been recovered
			        	
			        	//changes isStolen = false for that bike
			        	//StolenFalse obj = new StolenFalse();
			        	//obj.execute();
			        	
			        	Toast.makeText(getApplicationContext(), "Great to hear! We will cancel your report now.", Toast.LENGTH_LONG).show(); //send positive message to user!
						//obj1.execute();
			        	break;

			        case DialogInterface.BUTTON_NEGATIVE: //if user's bike has not been recovered
			        	
			        	String popup = "Do you still want to cancel your report?"; //verifying user's answer
						
						DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
						    @Override
						    /**
						     * method for when user indicates if he/she wants to cancel his/her report
						     * param - dialog: TODO what does a DialogInterface do?
						     * 		   which: TODO what does this do?
						     */
						    public void onClick(DialogInterface dialog, int which) {
						        switch (which){
						        
						        //cleans out report to restart
						        case DialogInterface.BUTTON_POSITIVE: //if user wants to cancel report
						        	Toast.makeText(getApplicationContext(), "Report has been cancelled.", Toast.LENGTH_LONG).show(); //tell them his/her report is cancelled
					        		//obj1.execute();
						        	break;

						        case DialogInterface.BUTTON_NEGATIVE: //if user does not want to cancel report, do nothing
						            break;
						        }
						    }
						};

						AlertDialog.Builder builder = new AlertDialog.Builder(TheFiveFrags.this); //builds alert dialog
						builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
						    .setNegativeButton("No", dialogClickListener).show(); //sets positive button to yes and negative button to no
						
			            break;
			        }
			    }
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(TheFiveFrags.this); //builds alert dialog
			builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show(); //sets positive button to yes and negative button to no
			
			editor.commit();
		}
	}
	
	/*private class RemoveReport extends AsyncTask<Void, Void, Void> {
		 
		@Override
		protected Void doInBackground(Void... params) {
			try {
				client.connect("bikenab.com");
				client.enterLocalPassiveMode();
				boolean login = client.login("bikenab", "Rufas123");

			    if (login) {
			    	if (client.listFiles("reports.ser").length == 1) { //do same for users.ser
			    		ftpDownload("/reports.ser", "/storage/sdcard/reports.ser");
			    		
			    		//deserialize file
			    		try
			            {
			                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/sdcard/reports.ser"));
			                reports = (ArrayList<String>) ois.readObject();
			            } catch(Exception ex) {
			            	
			            }
			    		
			    		reports.remove(prefs.getString("reportNum", ""));
			    		//need to remove bike pictures also
			    		FTPFile[] files = client.listFiles("/Report_Pictures/"+prefs.getString("reportNum","")+"/");
			    		
			    		for (int i = 2; i < files.length; i++) {
			    			client.deleteFile("/Report_Pictures/"+prefs.getString("reportNum","")+"/"+files[i].getName());
			    		}
			    		boolean res = client.removeDirectory("/Report_Pictures/"+prefs.getString("reportNum",""));
			    		
			    		//serialize file
			    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/storage/sdcard/reports.ser"))); //Select where you wish to save the file...
			            oos.writeObject(reports); // write the class as an 'object'
			            oos.flush();
			    		
			    		//send file to server
		                client.enterLocalPassiveMode(); // important!
		                client.setFileType(FTP.BINARY_FILE_TYPE);
		                String data = "/storage/sdcard/reports.ser";

		                FileInputStream in = new FileInputStream(new File(data));
		                boolean result = client.storeFile("/reports.ser", in);
		                if (result) Log.v("upload result", "succeeded");
		                
		                editor.putString("description", "");
						editor.putString("serial", "");
						editor.putStringSet("key", new HashSet<String>());
						editor.putBoolean("check",  false);
						editor.putStringSet("key",  new HashSet<String>());
						editor.commit(); 
			    	}
			    	
			    	client.logout();
	                client.disconnect();
			    }
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	private class StolenFalse extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			try {
        		client.connect("bikenab.com");
				client.enterLocalPassiveMode();
				boolean login = client.login("bikenab", "Rufas123");

			    if (login) {
			    	if (client.listFiles("users.ser").length == 1) { //do same for users.ser
			    		ftpDownload("/users.ser", "/storage/sdcard/users.ser");
			    		try
			            {
			                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/sdcard/users.ser"));
			                users = (HashMap<String, User>) ois.readObject();
			            } catch(Exception ex) {
			            	Log.v("Serialization Read Error : ",ex.getMessage());
			                ex.printStackTrace();
			            }
			    		
			    		//edit hashmap
			    		users.get(prefs.getString("name","")).reportStart = null;
			    		users.get(prefs.getString("name","")).reportFinish = null;
			    		users.get(prefs.getString("name","")).isReportFinished = false;
			    		
			    		//serialize file
			    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/storage/sdcard/users.ser"))); //Select where you wish to save the file...
			            oos.writeObject(users); // write the class as an 'object'
			            oos.flush();
			    		
			    		//send file to server
		                client.enterLocalPassiveMode(); // important!
		                client.setFileType(FTP.BINARY_FILE_TYPE);
		                String data = "/storage/sdcard/users.ser";

		                FileInputStream in = new FileInputStream(new File(data));
		                boolean result = client.storeFile("/users.ser", in);
		                if (result) Log.v("upload result", "succeeded");
			    	}
			    	
			    	if (client.listFiles("bikes.ser").length == 1) { //do same for users.ser
			    		ftpDownload("/bikes.ser", "/storage/sdcard/bikes.ser");
			    		
			    		//deserialize file
			    		try
			            {
			                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/sdcard/bikes.ser"));
			                bikes = (HashMap) ois.readObject();
			            } catch(Exception ex) {
			            	Log.v("Serialization Read Error : ",ex.getMessage());
			                ex.printStackTrace();
			            }
			    		
			    		//edit hashmap
			    		bikes.get(prefs.getString("serial","")).isStolen = false;
			    		bikes.get(prefs.getString("serial","")).latStolen = 0;
			    		bikes.get(prefs.getString("serial","")).longStolen = 0;
			    		
			    		//serialize file
			    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/storage/sdcard/bikes.ser"))); //Select where you wish to save the file...
			            oos.writeObject(bikes); // write the class as an 'object'
			            oos.flush();
			    		
			    		//send file to server
		                client.enterLocalPassiveMode(); // important!
		                client.setFileType(FTP.BINARY_FILE_TYPE);
		                String data = "/storage/sdcard/bikes.ser";

		                FileInputStream in = new FileInputStream(new File(data));
		                boolean result = client.storeFile("/bikes.ser", in);
		                if (result) Log.v("upload result", "succeeded");
			    	}
			    	
			    	client.logout();
	                client.disconnect();
			    } else {
			      System.out.println("Login fail...");
			    }
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
	} */
	
//------------------------------------------------------------------------------------------------//
	
	//Serial number search function
	public static class Section3Fragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		//premade
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section3, container, false);
			return rootView;
		}
	}
	
	public void search(View view) {
		//SearchMade obj = new SearchMade();
		//obj.execute();
	}
	
	/*private class SearchMade extends AsyncTask<Void, Void, Void> {
		protected Void doInBackground(Void... params) {
			try {
				client.connect("bikenab.com");
				client.enterLocalPassiveMode();
				boolean login = client.login("bikenab", "Rufas123");

			    if (login) {
			    	if (client.listFiles("users.ser").length == 1) {
			    		ftpDownload("/users.ser", "/storage/sdcard/users.ser");
			    		
			    		//deserialize file
			    		try
			            {
			                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/sdcard/users.ser"));
			                users = (HashMap) ois.readObject();
			            } catch(Exception ex) {
			            	Log.v("Serialization Read Error : ",ex.getMessage());
			                ex.printStackTrace();
			            }
			    		
			    		GPS gps = new GPS(getBaseContext());
			            float latitude = 0;
			            float longitude = 0;
						// check if GPS enabled		
				        if(gps.canGetLocation()){
				        	
				        	latitude = (float) gps.getLatitude();
				        	longitude = (float) gps.getLongitude();
				        }else{
				        	// Ask user to enable GPS/network in settings
				        	gps.showSettingsAlert();
				        }
				        
			            Search search = new Search(latitude, longitude, new Date());
			            
			    		//edit hashmap
			    		users.get(prefs.getString("name","")).searches.add(search);
			    		
			    		//serialize file
			    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/storage/sdcard/users.ser"))); //Select where you wish to save the file...
			            oos.writeObject(users); // write the class as an 'object'
			            oos.flush();
			    		
			    		//send file to server
		                client.enterLocalPassiveMode(); // important!
		                client.setFileType(FTP.BINARY_FILE_TYPE);
		                String data = "/storage/sdcard/users.ser";

		                FileInputStream in = new FileInputStream(new File(data));
		                boolean result = client.storeFile("/users.ser", in);
		                if (result) Log.v("upload result", "succeeded");
			    	}
			    	
			    	if (client.listFiles("bikes.ser").length == 1) { //do same for users.ser
			    		EditText editText = (EditText) findViewById(R.id.serial_num);
		    	    	String serial = editText.getText().toString();
		    	    	
			    		ftpDownload("/bikes.ser", "/storage/sdcard/bikes.ser");
			    		
			    		//deserialize file
			    		try
			            {
			                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/sdcard/bikes.ser"));
			                bikes = (HashMap) ois.readObject();
			            } catch(Exception ex) {
			            	Log.v("Serialization Read Error : ",ex.getMessage());
			                ex.printStackTrace();
			            }
			    		
			    		String description = bikes.get(serial).descript;
			    		
		    	    	//download bike photos and save them in sdcard using ftpdownload method
			    		//put paths into arraylist of strings and display using gridview
		                //display bike info in new activity
			    		
			    		if (bikes.get(serial).isStolen == true)
			    			Toast.makeText(getApplicationContext(), "Bike is stolen", Toast.LENGTH_LONG).show();
			    		else
			    			Toast.makeText(getApplicationContext(), "Bike is not stolen", Toast.LENGTH_LONG).show();
			    	}
			    	
			    	client.logout();
	    			client.disconnect();
			    }
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	} */
	
	//--------------------------------------------------------------------------------------------//
	
	public class Section4Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.personal_info, container, false);
			return rootView;
		}
		
		/**
		 * sets up fragment for user to put in bike info and submit
		 * param - savedInstanceState: TODO what does a Bundle do?
		 */
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState); //TODO what does this super to?
			
			EditText editText = (EditText) findViewById(R.id.name); //set editText to the one with ID of name
			String nombre = prefs.getString("name",""); //set this var to whatever is stored in name 
			editText.setText(nombre.substring(0,nombre.length()-2), TextView.BufferType.EDITABLE); //cut off the number to indicate duplicate
			
	    	editText = (EditText) findViewById(R.id.street_address); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("streetAddress", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
	    	
	    	editText = (EditText) findViewById(R.id.email_address); //set editText to the one with ID of email address
	    	editText.setText(prefs.getString("emailAddress", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in emailAddress
	    	
	    	editText = (EditText) findViewById(R.id.phone_number); //set editText to the one with ID of phone number
	    	editText.setText(prefs.getString("phoneNum", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in phoneNum
	    	
	    	editText = (EditText) findViewById(R.id.facebook); //set editText to the one with ID of facebook
	    	editText.setText(prefs.getString("fb", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in fb
	    	
	    	editText = (EditText) findViewById(R.id.twitter); //set editText to the one with ID of twitter
	    	editText.setText(prefs.getString("twitter", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in twitter 
	    	
	    	editText = (EditText) findViewById(R.id.google_plus);//set editText to the one with ID of google plus
	    	editText.setText(prefs.getString("googlePlus", ""), TextView.BufferType.EDITABLE);  //set this var to whatever is stored in googlePlus
		}
	}
	
	/**
	 * edit contact info
	 * @param view - TODO what does a View do?
	 */
	public void edit(View view) {
		EditText editText = (EditText) findViewById(R.id.name); //set editText to one with ID of name
    	name = editText.getText().toString()+"_0"; //sets name to whatever is in the editText plus a number for duplicate tracking
    	
    	editText = (EditText) findViewById(R.id.street_address); //set editText to one with ID of street address
    	streetAddress = editText.getText().toString(); //sets streetAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.email_address); //set editText to one with ID of email address
    	emailAddress = editText.getText().toString(); //sets emailAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.phone_number); //set editText to one with ID of phone_number
    	phoneNum = editText.getText().toString(); //sets phoneNum to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.facebook); //set editText to one with ID of facebook
    	fb = editText.getText().toString(); //sets fb to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.twitter); //set editText to one with ID of twitter
    	twitter = editText.getText().toString(); //sets twitter to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.google_plus); //set editText to one with ID of google_plus
    	googlePlus = editText.getText().toString(); //sets googlePlus to whatever is in the editText
    	
    	String first = "The following items have not been completed properly: "; //beginning of warning message
		String second = ""; //another part of the warning message
		String message = ""; //total warning message
		
		if (name.equals("")) { //if name is not filled in
			second += "name, "; //add name as an incompleted field
		}
		
		if (streetAddress.equals("")) { //if street address is not filled in
			second += "street address, "; //add street address as an incompleted field
		}
	
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //regex pattern
		Pattern ePattern = Pattern.compile(EMAIL_PATTERN); //TODO what does a Pattern do? what does compile do?
		Matcher eMatch = ePattern.matcher(emailAddress); //TODO what does Matcher do?
		
		if (!eMatch.matches()) { //TODO what does this condition do?
			second += "email address, "; //add email address as an incompleted field
		} 
		
		String expression = "(\\d{3}-){1,2}\\d{4}"; //a regex pattern
	    CharSequence inputStr = phoneNum; //TODO what does a CharSequence do?
	    Pattern pattern = Pattern.compile(expression); //TODO what does a pattern do? what does compile do?
	    Matcher matcher = pattern.matcher(inputStr); //TODO what does a Matcher do?
	    
	    if(!matcher.matches()) { //TODO what does this condition do?
	    	second += "phone number, "; //add phone number as an incompleted field
	    }
	    
	    //TODO validate street address, URLs
	    
		message = first + second; //make total warning message
		
    	if (!second.equals("")) { //if there are incompleted fields
    		new AlertDialog.Builder(this).setTitle("Not Completed").setMessage(message.substring(0,message.length()-2)).show();
    		//show user an error message saying that their info is incomplete
    	} else {
			//EditContact obj = new EditContact();
			//obj.execute();
		 
    		Intent intent = new Intent(this, TheFiveFrags.class); //go from this class to TheFiveFrags class
    		startActivity(intent);
		}
	}
	
	/*private class EditContact extends AsyncTask<Void, Void, Void> {
		 
		@Override
		protected Void doInBackground(Void... params) {
			try {
				
				client.connect("bikenab.com");
				client.enterLocalPassiveMode();
				boolean login = client.login("bikenab", "Rufas123");

			    if (login) {
			    	if (client.listFiles("users.ser").length == 1) { //do same for users.ser
			    		ftpDownload("/users.ser", "/storage/sdcard/users.ser");
			    		
			    		//deserialize file
			    		try
			            {
			                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/sdcard/users.ser"));
			                users = (HashMap) ois.readObject();
			            } catch(Exception ex) {
			            	Log.v("Serialization Read Error : ",ex.getMessage());
			                ex.printStackTrace();
			            }
			    		
			    		//edit hashmap
			    		users.remove(prefs.getString("name", ""));
			    		has(name);
			    		User user = new User(name, streetAddress, emailAddress, phoneNum, fb, twitter, googlePlus);
			    		users.put(name, user);
			    		
			    		//serialize file
			    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/storage/sdcard/users.ser"))); //Select where you wish to save the file...
			            oos.writeObject(users); // write the class as an 'object'
			            oos.flush();
			    		
			    		//send file to server
		                client.enterLocalPassiveMode(); // important!
		                client.setFileType(FTP.BINARY_FILE_TYPE);
		                String data = "/storage/sdcard/users.ser";

		                FileInputStream in = new FileInputStream(new File(data));
		                boolean result = client.storeFile("/users.ser", in);
		                if (result) Log.v("upload result", "succeeded");
		                
		                editor.putString("name", name);
				    	editor.putString("streetAddress", streetAddress);
				    	editor.putString("emailAddress", emailAddress);
				    	editor.putString("phoneNum", phoneNum);
				    	editor.putString("fb", fb);
				    	editor.putString("twitter", twitter);
				    	editor.putString("googlePlus", googlePlus);
						editor.commit();
			    	}
			    	
			    	client.logout();
	                client.disconnect();
			    }
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	} */
	
	//-------------------------------------------------------------------------------------------//
	
	public class Section5Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section5, container, false);
			return rootView;
		}
		
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	
	public class Section6Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section6, container, false);
			return rootView;
		}
		
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	
	public class Section7Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section7, container, false);
			return rootView;
		}
		
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	
		public class Section8Fragment extends Fragment {
			
			public static final String ARG_SECTION_NUMBER = "section_number";

			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				View rootView = inflater.inflate(R.layout.fragment_section8, container, false);
				return rootView;
			}
			
			public void onActivityCreated (Bundle savedInstanceState) {
				super.onActivityCreated(savedInstanceState);
			}
		}
		
		//-------------------------------------------------------------------------------------------//
		
		public class Section9Fragment extends Fragment {
			
			public static final String ARG_SECTION_NUMBER = "section_number";

			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				View rootView = inflater.inflate(R.layout.fragment_section9, container, false);
				return rootView;
			}
			
			public void onActivityCreated (Bundle savedInstanceState) {
				super.onActivityCreated(savedInstanceState);
			}
		}
		
		
}
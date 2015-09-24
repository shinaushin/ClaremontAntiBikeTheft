package com.example.claremontantibiketheft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
	public static long time = 0;
	
	public String serial = ""; //var for serial number of bike
	public String description = ""; //var for description of bike
	
	public String name = ""; //name of user
	public String streetAddress = ""; //street address of user
	public String emailAddress = ""; //email address of user
	public String phoneNum = ""; //phone number of user
	public String fb = ""; //fb url of user
	public String instagram = ""; //instagram handle of user
	public String twitter = ""; //twitter url of user
	
	public String shopBikeShop = ""; //name of bike shop
	public String nameBikeShop = ""; //name of user
	public String streetAddressBikeShop = ""; //street address of user
	public String emailAddressBikeShop = ""; //email address of user
	public String phoneNumBikeShop = ""; //phone number of user
	public String fbBikeShop = ""; //fb url of user
	public String instagramBikeShop = ""; //instagram handle of user
	public String twitterBikeShop = ""; //twitter url of user
	
	public String shopPawnShop = ""; //name of pawn shop
	public String namePawnShop = ""; //name of user
	public String streetAddressPawnShop = ""; //street address of user
	public String emailAddressPawnShop = ""; //email address of user
	public String phoneNumPawnShop = ""; //phone number of user
	public String fbPawnShop = ""; //fb url of user
	public String instagramPawnShop = ""; //instagram handle of user
	public String twitterPawnShop = ""; //twitter url of user
	
	public String deptPoliceDept = ""; //name of police department
	public String namePoliceDept = ""; //name of user
	public String streetAddressPoliceDept = ""; //street address of user
	public String emailAddressPoliceDept = ""; //email address of user
	public String phoneNumPoliceDept = ""; //phone number of user
	public String fbPoliceDept = ""; //fb url of user
	public String instagramPoliceDept = "";
	public String twitterPoliceDept = ""; //twitter url of user
	
	public String collegeCampus = "";
	public String nameCampus = ""; //name of user
	public String streetAddressCampus = ""; //street address of user
	public String emailAddressCampus = ""; //email address of user
	public String phoneNumCampus = ""; //phone number of user
	public String fbCampus = ""; //fb url of user
	public String instagramCampus = "";
	public String twitterCampus = ""; //twitter url of user
	
	public static final String PREFS_NAME = "MyPrefsFile"; //TODO what does this do?
	public static SharedPreferences prefs = null; //TODO what does this do?
	public SharedPreferences.Editor editor = null; //TODO what does this do?
	
	
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
				return getString(R.string.title_section5).toUpperCase(l);
			case 6:
				return getString(R.string.title_section6).toUpperCase(l);
			case 7:
				return getString(R.string.title_section7).toUpperCase(l);
			case 8:
				return getString(R.string.title_section8).toUpperCase(l);
			case 9:
				return getString(R.string.title_section9).toUpperCase(l);
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
		
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TODO what does this do?
		editor = prefs.edit(); //TODO what does this do?
		
		boolean firstrun = prefs.getBoolean("firstrun", true);
		if (firstrun) {
			editor.putBoolean("check", false);
			editor.putBoolean("report", false);
			editor.putBoolean("contact", false);
			editor.putBoolean("firstrun",  false);
			editor.commit();
		}
		
		setContentView(R.layout.activity_main); //open the layout of the main page
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager()); //TODO what does this do?

		mViewPager = (ViewPager) findViewById(R.id.pager); //TODO what does this do?
		mViewPager.setAdapter(mSectionsPagerAdapter); //TODO what does this do?
	}
	
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
	public static class TitleFragment extends Fragment {
		
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
		
		/**
		 * TODO what does this method do?
		 * param - savedInstanceState: TODO what does a Bundle do?
		 */
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState); //TODO what does this super to?
		}
	}

//-----------------------------------------------------------------------------------------------//
	
	/**
	 * displays fragment 1
	 * @author shinaushin
	 *
	 */
	public static class Section1Fragment extends Fragment {
		
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
		boolean contact = prefs.getBoolean("contact", false);
		boolean contact2 = prefs.getBoolean("contact", true);
		System.out.println(contact + " " + contact2);
		if (!contact) {
			Toast.makeText(getApplicationContext(), "Please enter in your contact info first on page 3.", Toast.LENGTH_LONG).show(); //displays warning message
		} else if (prefs.getBoolean("check",  true) == true) { //if check == true
			Toast.makeText(getApplicationContext(), "A bike is already registered. Please deregister first.", Toast.LENGTH_LONG).show(); //displays warning message
		} else {
			Intent intent = new Intent (this, RegisterBike.class); //goes from this class to RegisterBike class
			startActivity(intent);
		}
	}
	
	/**
	 * deregister a bike
	 * @param view - TODO what does a View do?
	 */
	public void deregister(View view) {
		boolean contact = prefs.getBoolean("contact", false);
		if (!contact) {
			Toast.makeText(getApplicationContext(), "Please enter in your contact info first on page 3.", Toast.LENGTH_LONG).show(); //displays warning message
		} else if (prefs.getBoolean("check", true) == false) { //if check is not true
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
			        	editor.putString("description", "");
						editor.putString("serial", "");
						editor.putStringSet("key", new HashSet<String>());
						editor.putBoolean("check",  false);
						editor.commit(); 
    					
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
		boolean contact = prefs.getBoolean("contact", false);
		if (!contact) {
			Toast.makeText(getApplicationContext(), "Please enter in your contact info first on page 3.", Toast.LENGTH_LONG).show(); //displays warning message
		} else if (prefs.getBoolean("check", false) == true) { //if check is true
			Intent intent = new Intent(this, ViewBikeInfo.class); //go from this class to ViewBikeInfo class
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "You have not registered a bike.", Toast.LENGTH_LONG).show(); //displays message saying that bike has not been registered
		}
	}
	
//------------------------------------------------------------------------------------------------//
	
	public static class Section2Fragment extends Fragment {
		
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
		boolean contact = prefs.getBoolean("contact", false);
		if (!contact) {
			Toast.makeText(getApplicationContext(), "Please enter in your contact info first on page 3.", Toast.LENGTH_LONG).show(); //displays warning message
		} else if (prefs.getBoolean("check",  true) == false) { //if check is false
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
			
//			GPS gps = new GPS(getBaseContext()); //TODO what does getBaseContext do?

			// check if GPS enabled		
//	        if(gps.canGetLocation()){
//	        	
//	        	float latitude = (float) gps.getLatitude(); //gets latitude of user's phone
//	        	float longitude = (float) gps.getLongitude(); //gets longitude of user's phone
//	        	
//	        	editor.putFloat("lat", latitude); //stores latitude
//	        	editor.putFloat("lat", longitude); //stores longitude
//	        	editor.commit(); //saves edits
//	        }else{
//	        	// Ask user to enable GPS/network in settings
//	        	gps.showSettingsAlert(this);
//	        }  
	        
	        Intent intent = new Intent(this, NewReport.class); //go from this class to NewReport class
	        startActivity(intent);
		}
	}
	
	/**
	 * allows user to view current report
	 * @param view - TODO what does View do?
	 */
	public void viewReport (View view) {
		boolean contact = prefs.getBoolean("contact", false);
		if (!contact) {
			Toast.makeText(getApplicationContext(), "Please enter in your contact info first on page 3.", Toast.LENGTH_LONG).show(); //displays warning message
		} else if (prefs.getBoolean("report",  true) == false) { //if there is no report in progress
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
		boolean contact = prefs.getBoolean("contact", false);
		if (!contact) {
			Toast.makeText(getApplicationContext(), "Please enter in your contact info first on page 3.", Toast.LENGTH_LONG).show(); //displays warning message
		} else if (prefs.getBoolean("report", true) == false) { //if there is no report
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
			        	editor.putString("description", "");
			    		editor.putString("serial", "");
			    		editor.putStringSet("key", new HashSet<String>());
			    		editor.putBoolean("report",  false);
			    		editor.putBoolean("reportFinish", false);
			    		editor.commit(); 
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
			        	Toast.makeText(getApplicationContext(), "Great to hear! We will cancel your report now.", Toast.LENGTH_LONG).show(); //send positive message to user!
			        	editor.putString("description", "");
			    		editor.putString("serial", "");
			    		editor.putStringSet("key", new HashSet<String>());
			    		editor.putBoolean("report",  false);
			    		editor.putBoolean("reportFinish", false);
			    		editor.putStringSet("key",  new HashSet<String>());
			    		editor.commit(); 
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
						        	editor.putString("description", "");
						    		editor.putString("serial", "");
						    		editor.putStringSet("key", new HashSet<String>());
						    		editor.putBoolean("report",  false);
						    		editor.putBoolean("reportFinish", false);
						    		editor.putStringSet("key",  new HashSet<String>());
						    		editor.commit(); 
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
	
	/**
	 * 
	 * @param view - TODO what does this do?
	 */
	public void onClick (View view) {
		EditText editText = (EditText) findViewById(R.id.serial_num);
		String serialNum = editText.getText().toString();
		
		String second = "";
		
		if (serialNum.equals("")) { //if no report number was added
			second += "serial number, "; //adds in warning of the fact that police report number is not filled in
		}
		
		boolean contact = prefs.getBoolean("contact", false);
		if (!contact) {
			new AlertDialog.Builder(this).setTitle("Incomplete").setMessage("Please enter in your contact info first on page 3.").show();
		} else if (!second.equals("")) {
			new AlertDialog.Builder(this).setTitle("Incomplete").setMessage("Please enter a serial number.").show();
		} else {
			Intent intent = new Intent(this, SearchSerialNum.class);
			startActivity(intent);
		}
	}
	
	//--------------------------------------------------------------------------------------------//
	
	public static class Section4Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.edit_contact_info, container, false);
			return rootView;
		}
		
		/**
		 * sets up fragment for user to edit contact info and submit
		 * param - savedInstanceState: TODO what does a Bundle do?
		 */
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState); //TODO what does this super to?
			
			EditText editText = (EditText) getActivity().findViewById(R.id.name); //set editText to the one with ID of name
			String nombre = prefs.getString("name",""); //set this var to whatever is stored in name 
			if (nombre.length() == 0) {
				editText.setText("", TextView.BufferType.EDITABLE);
			} else {
				editText.setText(nombre.substring(0,nombre.length()-2), TextView.BufferType.EDITABLE); //cut off the number to indicate duplicate
			}
			
	    	editText = (EditText) getActivity().findViewById(R.id.street_address); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("streetAddress", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.email_address); //set editText to the one with ID of email address
	    	editText.setText(prefs.getString("emailAddress", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in emailAddress
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.phone_number); //set editText to the one with ID of phone number
	    	editText.setText(prefs.getString("phoneNum", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in phoneNum
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.facebook); //set editText to the one with ID of facebook
	    	editText.setText(prefs.getString("fb", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in fb
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.instagram); //set editText to the one with ID of instagram
	    	editText.setText(prefs.getString("instagram", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in instagram
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.twitter); //set editText to the one with ID of twitter
	    	editText.setText(prefs.getString("twitter", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in twitter 
		}
	}
	
	/**
	 * edit contact info
	 * @param view - TODO what does a View do?
	 */
	public void editUserContactInfo(View view) {
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
    	
    	editText = (EditText) findViewById(R.id.instagram); //set editText to one with ID of instagram
    	instagram = editText.getText().toString(); //sets instagram to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.twitter); //set editText to one with ID of twitter
    	twitter = editText.getText().toString(); //sets twitter to whatever is in the editText
    	
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
    		editor.putString("name", name);
	    	editor.putString("streetAddress", streetAddress);
	    	editor.putString("emailAddress", emailAddress);
	    	editor.putString("phoneNum", phoneNum);
	    	editor.putString("fb", fb);
	    	editor.putString("instagram", instagram);
	    	editor.putString("twitter", twitter);
	    	editor.putBoolean("contact", true);
	    	editor.commit();
	    	
	    	Intent intent = new Intent(this, TheFiveFrags.class); //go from this class to TheFiveFrags class
    		startActivity(intent);
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	//Bikeshop
	public static class Section5Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section5, container, false);
			return rootView;
		}
		
		/**
		 * sets up fragment for user to put in bike info and submit
		 * param - savedInstanceState: TODO what does a Bundle do?
		 */
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState); //TODO what does this super to?
			
			EditText editText = (EditText) getActivity().findViewById(R.id.shop_bikeshop); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("shopBikeShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
	    	
			editText = (EditText) getActivity().findViewById(R.id.name_bikeshop); //set editText to the one with ID of name
			String nombre = prefs.getString("nameBikeShop",""); //set this var to whatever is stored in name 
			if (!nombre.equals("")) //if num is already a bike's serial number
				editText.setText(nombre.substring(0,nombre.length()-2), TextView.BufferType.EDITABLE); //display the serial number
			else
				editText.setText("", TextView.BufferType.EDITABLE); //set the edit text box as blank
		
	    	editText = (EditText) getActivity().findViewById(R.id.street_address_bikeshop); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("streetAddressBikeShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.email_address_bikeshop); //set editText to the one with ID of email address
	    	editText.setText(prefs.getString("emailAddressBikeShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in emailAddress
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.phone_number_bikeshop); //set editText to the one with ID of phone number
	    	editText.setText(prefs.getString("phoneNumBikeShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in phoneNum
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.facebook_bikeshop); //set editText to the one with ID of facebook
	    	editText.setText(prefs.getString("fbBikeShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in fb
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.instagram_bikeshop); //set editText to the one with ID of instagram
	    	editText.setText(prefs.getString("instagramBikeShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in instagram
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.twitter_bikeshop); //set editText to the one with ID of twitter
	    	editText.setText(prefs.getString("twitterBikeShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in twitter 
		}
	}
	
	/**
	 * edit contact info for bikeshop
	 * @param view - TODO what does a View do?
	 */
	public void editBikeShop(View view) {
		EditText editText = (EditText) findViewById(R.id.shop_bikeshop); //set editText to one with ID of name
    	shopBikeShop = editText.getText().toString(); //sets name to whatever is in the editText plus a number for duplicate tracking
    	
		editText = (EditText) findViewById(R.id.name_bikeshop); //set editText to one with ID of name
    	nameBikeShop = editText.getText().toString()+"_0"; //sets name to whatever is in the editText plus a number for duplicate tracking
    	
    	editText = (EditText) findViewById(R.id.street_address_bikeshop); //set editText to one with ID of street address
    	streetAddressBikeShop = editText.getText().toString(); //sets streetAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.email_address_bikeshop); //set editText to one with ID of email address
    	emailAddressBikeShop = editText.getText().toString(); //sets emailAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.phone_number_bikeshop); //set editText to one with ID of phone_number
    	phoneNumBikeShop = editText.getText().toString(); //sets phoneNum to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.facebook_bikeshop); //set editText to one with ID of facebook
    	fbBikeShop = editText.getText().toString(); //sets fb to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.instagram_bikeshop); //set editText to one with ID of instagram
    	instagramBikeShop = editText.getText().toString(); //sets instagram to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.twitter_bikeshop); //set editText to one with ID of twitter
    	twitterBikeShop = editText.getText().toString(); //sets twitter to whatever is in the editText
    	
    	String first = "The following items have not been completed properly: "; //beginning of warning message
		String second = ""; //another part of the warning message
		String message = ""; //total warning message
		
		if (shopBikeShop.equals("")) { //if name is not filled in
			second += "name of shop, "; //add name as an incompleted field
		}
		
		if (nameBikeShop.equals("")) { //if name is not filled in
			second += "name of user, "; //add name as an incompleted field
		}
		
		if (streetAddressBikeShop.equals("")) { //if street address is not filled in
			second += "street address, "; //add street address as an incompleted field
		}
	
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //regex pattern
		Pattern ePattern = Pattern.compile(EMAIL_PATTERN); //TODO what does a Pattern do? what does compile do?
		Matcher eMatch = ePattern.matcher(emailAddressBikeShop); //TODO what does Matcher do?
		
		if (!eMatch.matches()) { //TODO what does this condition do?
			second += "email address, "; //add email address as an incompleted field
		} 
		
		String expression = "(\\d{3}-){1,2}\\d{4}"; //a regex pattern
	    CharSequence inputStr = phoneNumBikeShop; //TODO what does a CharSequence do?
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
    		editor.putString("nameBikeShop", nameBikeShop);
	    	editor.putString("streetAddressBikeShop", streetAddressBikeShop);
	    	editor.putString("emailAddressBikeShop", emailAddressBikeShop);
	    	editor.putString("phoneNumBikeShop", phoneNumBikeShop);
	    	editor.putString("fbBikeShop", fbBikeShop);
	    	editor.putString("twitterBikeShop", twitterBikeShop);
	    	editor.putString("instagramBikeShop", instagramBikeShop);
			editor.commit();
		 
    		Intent intent = new Intent(this, TheFiveFrags.class); //go from this class to TheFiveFrags class
    		startActivity(intent);
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	//Pawnshop
	public static class Section6Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section6, container, false);
			return rootView;
		}
		
		/**
		 * sets up fragment for user to put in bike info and submit
		 * param - savedInstanceState: TODO what does a Bundle do?
		 */
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState); //TODO what does this super to?
			
			EditText editText = (EditText) getActivity().findViewById(R.id.shop_pawnshop); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("shopPawnShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
	    	
			editText = (EditText) getActivity().findViewById(R.id.name_pawnshop); //set editText to the one with ID of name
			String nombre = prefs.getString("namePawnShop",""); //set this var to whatever is stored in name 
			if (!nombre.equals("")) //if num is already a bike's serial number
				editText.setText(nombre.substring(0,nombre.length()-2), TextView.BufferType.EDITABLE); //display the serial number
			else
				editText.setText("", TextView.BufferType.EDITABLE); //set the edit text box as blank
			
	    	editText = (EditText) getActivity().findViewById(R.id.street_address_pawnshop); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("streetAddressPawnShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.email_address_pawnshop); //set editText to the one with ID of email address
	    	editText.setText(prefs.getString("emailAddressPawnShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in emailAddress
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.phone_number_pawnshop); //set editText to the one with ID of phone number
	    	editText.setText(prefs.getString("phoneNumPawnShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in phoneNum
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.facebook_pawnshop); //set editText to the one with ID of facebook
	    	editText.setText(prefs.getString("fbPawnShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in fb
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.instagram_pawnshop); //set editText to the one with ID of instagram
	    	editText.setText(prefs.getString("instagramPawnShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in instagram
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.twitter_pawnshop); //set editText to the one with ID of twitter
	    	editText.setText(prefs.getString("twitterPawnShop", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in twitter 
	    }
	}
	
	/**
	 * edit contact info for bikeshop
	 * @param view - TODO what does a View do?
	 */
	public void editPawnShop(View view) {
		EditText editText = (EditText) findViewById(R.id.shop_pawnshop); //set editText to one with ID of name
    	shopPawnShop = editText.getText().toString(); //sets name to whatever is in the editText plus a number for duplicate tracking
    	
		editText = (EditText) findViewById(R.id.name_pawnshop); //set editText to one with ID of name
    	namePawnShop = editText.getText().toString()+"_0"; //sets name to whatever is in the editText plus a number for duplicate tracking
    	
    	editText = (EditText) findViewById(R.id.street_address_pawnshop); //set editText to one with ID of street address
    	streetAddressPawnShop = editText.getText().toString(); //sets streetAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.email_address_pawnshop); //set editText to one with ID of email address
    	emailAddressPawnShop = editText.getText().toString(); //sets emailAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.phone_number_pawnshop); //set editText to one with ID of phone_number
    	phoneNumPawnShop = editText.getText().toString(); //sets phoneNum to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.facebook_pawnshop); //set editText to one with ID of facebook
    	fbPawnShop = editText.getText().toString(); //sets fb to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.instagram_pawnshop); //set editText to one with ID of instagram
    	instagramPawnShop = editText.getText().toString(); //sets instagram to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.twitter_pawnshop); //set editText to one with ID of twitter
    	twitterPawnShop = editText.getText().toString(); //sets twitter to whatever is in the editText
    	
    	String first = "The following items have not been completed properly: "; //beginning of warning message
		String second = ""; //another part of the warning message
		String message = ""; //total warning message
		
		if (shopPawnShop.equals("")) { //if name is not filled in
			second += "name of shop, "; //add name as an incompleted field
		}
		
		if (namePawnShop.equals("")) { //if name is not filled in
			second += "name of user, "; //add name as an incompleted field
		}
		
		if (streetAddressPawnShop.equals("")) { //if street address is not filled in
			second += "street address, "; //add street address as an incompleted field
		}
	
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //regex pattern
		Pattern ePattern = Pattern.compile(EMAIL_PATTERN); //TODO what does a Pattern do? what does compile do?
		Matcher eMatch = ePattern.matcher(emailAddressPawnShop); //TODO what does Matcher do?
		
		if (!eMatch.matches()) { //TODO what does this condition do?
			second += "email address, "; //add email address as an incompleted field
		} 
		
		String expression = "(\\d{3}-){1,2}\\d{4}"; //a regex pattern
	    CharSequence inputStr = phoneNumPawnShop; //TODO what does a CharSequence do?
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
    		editor.putString("namePawnShop", namePawnShop);
	    	editor.putString("streetAddressPawnShop", streetAddressPawnShop);
	    	editor.putString("emailAddressPawnShop", emailAddressPawnShop);
	    	editor.putString("phoneNumPawnShop", phoneNumPawnShop);
	    	editor.putString("fbPawnShop", fbPawnShop);
	    	editor.putString("instagramPawnShop", instagramPawnShop);
	    	editor.putString("twitterPawnShop", twitterPawnShop);
	    	editor.commit();
		 
    		Intent intent = new Intent(this, TheFiveFrags.class); //go from this class to TheFiveFrags class
    		startActivity(intent);
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	//Law Enforcement
	public static class Section7Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section7, container, false);
			return rootView;
		}
		
		/**
		 * sets up fragment for user to put in bike info and submit
		 * param - savedInstanceState: TODO what does a Bundle do?
		 */
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState); //TODO what does this super to?
			
			EditText editText = (EditText) getActivity().findViewById(R.id.dept_police); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("deptPoliceDept", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
			
			editText = (EditText) getActivity().findViewById(R.id.name_police); //set editText to the one with ID of name
			String nombre = prefs.getString("namePoliceDept",""); //set this var to whatever is stored in name 
			if (!nombre.equals("")) //if num is already a bike's serial number
				editText.setText(nombre.substring(0,nombre.length()-2), TextView.BufferType.EDITABLE); //display the serial number
			else
				editText.setText("", TextView.BufferType.EDITABLE); //set the edit text box as blank
			
	    	editText = (EditText) getActivity().findViewById(R.id.street_address_police); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("streetAddressPoliceDept", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.email_address_police); //set editText to the one with ID of email address
	    	editText.setText(prefs.getString("emailAddressPoliceDept", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in emailAddress
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.phone_number_police); //set editText to the one with ID of phone number
	    	editText.setText(prefs.getString("phoneNumPoliceDept", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in phoneNum
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.facebook_police); //set editText to the one with ID of facebook
	    	editText.setText(prefs.getString("fbPoliceDept", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in fb
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.instagram_police); //set editText to the one with ID of instagram
	    	editText.setText(prefs.getString("instagramPoliceDept", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in instagram
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.twitter_police); //set editText to the one with ID of twitter
	    	editText.setText(prefs.getString("twitterPoliceDept", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in twitter 
		}
	}
	
	/**
	 * edit contact info for bikeshop
	 * @param view - TODO what does a View do?
	 */
	public void editLawEnforcement(View view) {
		EditText editText = (EditText) findViewById(R.id.dept_police); //set editText to one with ID of name
    	deptPoliceDept = editText.getText().toString(); //sets name to whatever is in the editText plus a number for duplicate tracking
    	
		editText = (EditText) findViewById(R.id.name_police); //set editText to one with ID of name
    	namePoliceDept = editText.getText().toString()+"_0"; //sets name to whatever is in the editText plus a number for duplicate tracking
    	
    	editText = (EditText) findViewById(R.id.street_address_police); //set editText to one with ID of street address
    	streetAddressPoliceDept = editText.getText().toString(); //sets streetAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.email_address_police); //set editText to one with ID of email address
    	emailAddressPoliceDept = editText.getText().toString(); //sets emailAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.phone_number_police); //set editText to one with ID of phone_number
    	phoneNumPoliceDept = editText.getText().toString(); //sets phoneNum to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.facebook_police); //set editText to one with ID of facebook
    	fbPoliceDept = editText.getText().toString(); //sets fb to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.instagram_police); //set editText to one with ID of instagram
    	instagramPoliceDept = editText.getText().toString(); //sets instagram to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.twitter_police); //set editText to one with ID of twitter
    	twitterPoliceDept = editText.getText().toString(); //sets twitter to whatever is in the editText
    	
    	String first = "The following items have not been completed properly: "; //beginning of warning message
		String second = ""; //another part of the warning message
		String message = ""; //total warning message
		
		if (deptPoliceDept.equals("")) { //if name is not filled in
			second += "name of police department, "; //add name as an incompleted field
		}
		
		if (namePoliceDept.equals("")) { //if name is not filled in
			second += "name of user, "; //add name as an incompleted field
		}
		
		if (streetAddressPoliceDept.equals("")) { //if street address is not filled in
			second += "street address, "; //add street address as an incompleted field
		}
	
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //regex pattern
		Pattern ePattern = Pattern.compile(EMAIL_PATTERN); //TODO what does a Pattern do? what does compile do?
		Matcher eMatch = ePattern.matcher(emailAddressPoliceDept); //TODO what does Matcher do?
		
		if (!eMatch.matches()) { //TODO what does this condition do?
			second += "email address, "; //add email address as an incompleted field
		} 
		
		String expression = "(\\d{3}-){1,2}\\d{4}"; //a regex pattern
	    CharSequence inputStr = phoneNumPoliceDept; //TODO what does a CharSequence do?
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
    		editor.putString("namePoliceDept", namePoliceDept);
	    	editor.putString("streetAddressPoliceDept", streetAddressPoliceDept);
	    	editor.putString("emailAddressPoliceDept", emailAddressPoliceDept);
	    	editor.putString("phoneNumPoliceDept", phoneNumPoliceDept);
	    	editor.putString("fbPoliceDept", fbPoliceDept);
	    	editor.putString("instagramPoliceDept", instagramPoliceDept);
	    	editor.putString("twitterPoliceDept", twitterPoliceDept);
	    	editor.commit();
		 
    		Intent intent = new Intent(this, TheFiveFrags.class); //go from this class to TheFiveFrags class
    		startActivity(intent);
		}
	}
	
	//-------------------------------------------------------------------------------------------//
	//Campus Security
	public static class Section8Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section8, container, false);
			return rootView;
		}
		
		/**
		 * sets up fragment for campus contact to put in info and submit
		 * param - savedInstanceState: TODO what does a Bundle do?
		 */
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState); //TODO what does this super to?
			
			EditText editText = (EditText) getActivity().findViewById(R.id.college_campus); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("collegeCampus", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
	    	
			editText = (EditText) getActivity().findViewById(R.id.name_campus); //set editText to the one with ID of name
			String nombre = prefs.getString("nameCampus",""); //set this var to whatever is stored in name 
			if (!nombre.equals("")) //if num is already a bike's serial number
				editText.setText(nombre.substring(0,nombre.length()-2), TextView.BufferType.EDITABLE); //display the serial number
			else
				editText.setText("", TextView.BufferType.EDITABLE); //set the edit text box as blank
			
	    	editText = (EditText) getActivity().findViewById(R.id.street_address_campus); //set editText to the one with ID of street address
	    	editText.setText(prefs.getString("streetAddressCampus", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in streetAddress 
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.email_address_campus); //set editText to the one with ID of email address
	    	editText.setText(prefs.getString("emailAddressCampus", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in emailAddress
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.phone_number_campus); //set editText to the one with ID of phone number
	    	editText.setText(prefs.getString("phoneNumCampus", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in phoneNum
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.facebook_campus); //set editText to the one with ID of facebook
	    	editText.setText(prefs.getString("fbCampus", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in fb
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.instagram_campus); //set editText to the one with ID of instagram
	    	editText.setText(prefs.getString("instagramCampus", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in instagram
	    	
	    	editText = (EditText) getActivity().findViewById(R.id.twitter_campus); //set editText to the one with ID of twitter
	    	editText.setText(prefs.getString("twitterCampus", ""), TextView.BufferType.EDITABLE); //set this var to whatever is stored in twitter 
		}
	}
	
	/**
	 * edit contact info for bikeshop
	 * @param view - TODO what does a View do?
	 */
	public void editCampus(View view) {
		EditText editText = (EditText) findViewById(R.id.college_campus); //set editText to one with ID of name
    	collegeCampus = editText.getText().toString(); //sets name to whatever is in the editText plus a number for duplicate tracking
    	
		editText = (EditText) findViewById(R.id.name_campus); //set editText to one with ID of name
    	nameCampus = editText.getText().toString()+"_0"; //sets name to whatever is in the editText plus a number for duplicate tracking
    	
    	editText = (EditText) findViewById(R.id.street_address_campus); //set editText to one with ID of street address
    	streetAddressCampus = editText.getText().toString(); //sets streetAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.email_address_campus); //set editText to one with ID of email address
    	emailAddressCampus = editText.getText().toString(); //sets emailAddress to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.phone_number_campus); //set editText to one with ID of phone_number
    	phoneNumCampus = editText.getText().toString(); //sets phoneNum to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.facebook_campus); //set editText to one with ID of facebook
    	fbCampus = editText.getText().toString(); //sets fb to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.instagram_campus); //set editText to one with ID of instagram
    	instagramCampus = editText.getText().toString(); //sets instagram to whatever is in the editText
    	
    	editText = (EditText) findViewById(R.id.twitter_campus); //set editText to one with ID of twitter
    	twitterCampus = editText.getText().toString(); //sets twitter to whatever is in the editText
    	
    	String first = "The following items have not been completed properly: "; //beginning of warning message
		String second = ""; //another part of the warning message
		String message = ""; //total warning message
		
		if (collegeCampus.equals("")) { //if name is not filled in
			second += "name of college, "; //add name as an incompleted field
		}
		
		if (nameCampus.equals("")) { //if name is not filled in
			second += "name of user, "; //add name as an incompleted field
		}
		
		if (streetAddressCampus.equals("")) { //if street address is not filled in
			second += "street address, "; //add street address as an incompleted field
		}
	
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //regex pattern
		Pattern ePattern = Pattern.compile(EMAIL_PATTERN); //TODO what does a Pattern do? what does compile do?
		Matcher eMatch = ePattern.matcher(emailAddressCampus); //TODO what does Matcher do?
		
		if (!eMatch.matches()) { //TODO what does this condition do?
			second += "email address, "; //add email address as an incompleted field
		} 
		
		String expression = "(\\d{3}-){1,2}\\d{4}"; //a regex pattern
	    CharSequence inputStr = phoneNumCampus; //TODO what does a CharSequence do?
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
    		editor.putString("nameCampus", nameCampus);
	    	editor.putString("streetAddressCampus", streetAddressCampus);
	    	editor.putString("emailAddressCampus", emailAddressCampus);
	    	editor.putString("phoneNumCampus", phoneNumCampus);
	    	editor.putString("fbCampus", fbCampus);
	    	editor.putString("instagramCampus", instagramCampus);
	    	editor.putString("twitterCampus", twitterCampus);
	    	editor.commit();
		 
    		Intent intent = new Intent(this, TheFiveFrags.class); //go from this class to TheFiveFrags class
    		startActivity(intent);
		}
	}
		
	//-------------------------------------------------------------------------------------------//
	//Fundraising
	public static class Section9Fragment extends Fragment {
		
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
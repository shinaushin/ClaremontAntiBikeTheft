package com.example.claremontantibiketheft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.example.claremontantibiketheft.TheFiveFrags.SectionsPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class RegisterBike extends FragmentActivity {
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars	
		
	SectionsPagerAdapter mSectionsPagerAdapter; //TODO what does a SectionsPagerAdapter do?
	ViewPager mViewPager; //TODO what does a ViewPager do?
	protected ImageLoader imageLoader = ImageLoader.getInstance(); //TODO what does a ImageLoader do?
	public static Button button; //var for button
	public Bike bike = null; //var for bike
	public String serial = ""; //var for serial number of bike
	public String description = ""; //var for description of bike
	public String name = ""; //var of user's name
	public String streetAddress = ""; //var of user's street address
	public String emailAddress = ""; //var of user's email address
	public String phoneNum = ""; //var of user's phone number
	public String fb = ""; //var of user's fb url
	public String twitter = ""; //var of user's twitter url
	public String linkedin = ""; //var of user's linkedin url
	public String googlePlus = ""; //var of user's googlePlus url
	public FTPClient client = new FTPClient(); 
	public HashMap<String, Bike> bikes = new HashMap<String, Bike>(); //hashmap of serial number string and bike objects
	
	public static final String PREFS_NAME = "MyPrefsFile"; //TODO what does this do?
	public SharedPreferences prefs = null; //TODO what does this do?
	public SharedPreferences.Editor editor = null; //TODO what does this do?
	
	public ArrayList<String> imageUrls = new ArrayList<String>(); //TODO what does this do?
	public HashSet<String> uri = new HashSet<String>(); //TODO what does this do?
	public long time = 0; //time for when the incident report is put in...I think
	
	/**
	 * TODO what does this method do?
	 * param: savedInstanceState - TODO what does this do?
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //TODO why does it have to super?
		setContentView(R.layout.register_bike); //sets content to specified layout
		
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TODO what does this do?
		editor = prefs.edit(); //TODO what does this do?
		
		GridView grid = (GridView) findViewById(R.id.gridview); //sets up a gridview object of bike pictures
		grid.setBackgroundColor(Color.LTGRAY); //sets background of grid to light gray
		GridBikeInfo ga = new GridBikeInfo(this); //TODO what does this GridBikeInfo object do?
	    grid.setAdapter(ga); //TODO what does setAdapter do?
	    
	    Button photoButton = (Button) this.findViewById(R.id.take); //button to take photos of bike
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	time = System.currentTimeMillis();
                File f = new File(android.os.Environment.getExternalStorageDirectory(), time+".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);
            }
        }); //code for clicking the button to take photo
		
		addListenerOnButton(); //goes to addListenerOnButton method
		
		EditText editText = (EditText) findViewById(R.id.serial); //edit text box for bike's serial number 
		String num = prefs.getString("serial",""); //num takes on the value of whatever serial was stored as
		if (!num.equals("")) //if num is already a bike's serial number
			editText.setText(num.substring(0,num.length()-2), TextView.BufferType.EDITABLE); //display the serial number
		else
			editText.setText("", TextView.BufferType.EDITABLE); //set the edit text box as blank
		
		editText = (EditText) findViewById(R.id.description); //sets editText to the text box with specified ID
		editText.setText(prefs.getString("description", ""), TextView.BufferType.EDITABLE); //sets editText to the description of the bike
	}
	
	/**
	 * TODO when is this method called?
	 * param: requestCode - TODO what does this do?
	 * 		  resultCode - TODO what does this do?
	 * 		  data - TODO what does this do?
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		if (resultCode == RESULT_OK) { //TODO why would resultCode equal this?
            if (requestCode == 1) { //TODO why would requestCode equal 1?
                File f = new File(Environment.getExternalStorageDirectory().toString()); //TODO what file is assigned to this var?
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals(time+".jpg")) { //
                        f = temp;
                        HashSet<String> setter = (HashSet<String>) prefs.getStringSet("key", new HashSet<String>()); //TODO what does this do?
                        setter.add(Uri.fromFile(f).toString().substring(7)); //TODO what does this do? why substring 7?
                        editor.putStringSet("key",  setter); //puts the value of setter to "key"
                        editor.commit(); //saves edits
                        GridView grid = (GridView) findViewById(R.id.gridview); //sets grid to grid with specified ID
                		grid.setBackgroundColor(Color.LTGRAY); //sets background of grid to light gray
                		GridBikeInfo ga = new GridBikeInfo(this); //TODO what does this do?
                	    grid.setAdapter(ga); //TODO what does setAdapter do?
                        break;
                    }
                }
            }
		}
	}
	
	/**
	 * adds button listener on describing what photos the user should upload
	 */
	public void addListenerOnButton() {
		 
		ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton); //sets imageButton to button with specified ID
 
		imageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String photo = "";
				photo += "1) A picture of you and your bike.";
				photo += "\n2) A picture of the bike by itself.";
				photo += "\n3) A picture of the bike's serial number.";
				photo += "\n4) A picture of the sales receipt.";
				photo += "\n\nFeel free to upload 2 more photos after these four.";
				new AlertDialog.Builder(RegisterBike.this).setTitle("The 4 Photos You Need to Upload").setMessage(photo).show();
			}
		}); //when button is clicked, it'll show the alert dialog
	}
	
	/**
	 * TODO why is it premade? what is this method used for?
	 */
	@Override
	public void onStop() {
		imageLoader.stop();
		super.onStop();
	}
	
	/**
	 * TODO what does this method do?
	 * @param view - TODO what does this do?
	 */
	public void choose (View view) {
		EditText editText = (EditText) findViewById(R.id.serial); //editText is assigned to text field with specified ID
		serial = editText.getText().toString()+"_0"; //assigns serial to the serial string in editText with an extra number just in case there are duplicates
		
		editText = (EditText) findViewById(R.id.description); //sets up editText to a text field with specified ID
		description = editText.getText().toString(); //assigned description to the description string in editText
		
		editor.putString("serial", serial); //puts serial into "serial"
		editor.putString("description", description); //puts description into "description"
		editor.commit(); //saves edits
		
		Intent intent = new Intent(this, GridViewTesterBike.class); //goes from this class file to the other
		startActivity(intent);
	}
	
	/**
	 * clears all the pictures in the gridview
	 * @param view - TODO what does this do?
	 */
	public void clear (View view) {
		editor.putStringSet("key",  new HashSet<String>()); //sets key to a new hashset
        editor.commit(); //saves edits
		GridView grid = (GridView) findViewById(R.id.gridview); //assigned grid to the gridview with specified ID
		grid.setBackgroundColor(Color.LTGRAY); //sets grid background color to light gray
		GridBikeInfo ga = new GridBikeInfo(this); //TODO why do we need GridBikeInfo object?
	    grid.setAdapter(ga); //TODO what does setAdapter do?
	}
	
	/**
	 * method for when user submits bike information and checks everything has been put in correctly
	 * @param v - TODO what does it do?
	 */
	public void btnChoosePhotosClick(View v){
		EditText editText = (EditText) findViewById(R.id.serial); //assigned editText to the text field with specified ID
		serial = editText.getText().toString()+"_0"; //serial is assigned the serial number string plus zero just in case there are duplicates
		boolean seri = serial.equals(""); //checks if no serial number has been entered
		
		editText = (EditText) findViewById(R.id.description); //assigns editText to the text field with specified ID
		description = editText.getText().toString(); //description is assigned the string from editText
		boolean descript = description.equals(""); //checks if no description has been entered
		
		HashSet<String> uris = (HashSet<String>) prefs.getStringSet("key", null); //assigns uris to whatever is stored in key
		
		String warning = ""; //initializes warning string
		
		if (seri) {
			warning += "Please fill in serial number.\n"; //notifies user that serial number has to be filled in
		}
		
		if (descript) {
			warning += "Please fill in the bike information section.\n"; //notifies user that description has to be filled in
		}
		
		if (uris.size() < 4) {
			warning += "You must select at least 4 pictures. Please check photo requirements."; //notifies user that they must select at least 4 pictures
		}
		
		if (uris.size() > 6) {
			warning += "You can only select a maximum of 6 photos. Please check photo requirements."; //notifies user that they cannot select more than 6 pictures
		}
		
		if (!warning.equals("")) {
			new AlertDialog.Builder(this).setTitle("Not Completed").setMessage(warning).show(); //alerts user if it is not completed and still submitted
		} else {
			
			//SendBike obj = new SendBike();
			//obj.execute();
			
			//specifically fragment 1
			Intent intent = new Intent(this, TheFiveFrags.class); //moves from this class to TheFiveFrags class
			startActivity(intent);
		}
	}
	
	/**
	 * send bike object and pictures to lAMP server
	 */
	/*private class SendBike extends AsyncTask<Void, Void, Void> {
		 
		@Override
		protected Void doInBackground(Void... params) {
			try {
				client.connect("bikenab.com");
				client.enterLocalPassiveMode();
				boolean login = client.login("bikenab", "Rufas123");

			    if (login) {
			    	if (client.listFiles("bikes.ser").length == 1) { //do same for users.ser
			    		ftpDownload("/bikes.ser", "/storage/sdcard/bikes.ser");
			    		
			    		//deserialize file
			    		try
			            {
			                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/storage/sdcard/bikes.ser"));
			                bikes = (HashMap) ois.readObject();
			            } catch(Exception ex) {
			            	
			            }
			    		
			    		//edit hashmap
			    		has(serial);
			    		bike = new Bike (serial, description, 0.0, 0.0);
			    		bikes.put(serial, bike);
			    		
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
			    	} else {
			    		try
			            {
			    			bike = new Bike (serial, description, 0.0, 0.0);
			    			bikes.put(prefs.getString("serial",""), bike);
			    			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/storage/sdcard/bikes.ser"))); //Select where you wish to save the file...
			    			oos.writeObject(bikes); // write the class as an 'object'
			    			oos.flush(); // flush the stream to insure all of the information was written 
			               
			    			//send file to server
			    			client.enterLocalPassiveMode(); // important!
			    			client.setFileType(FTP.BINARY_FILE_TYPE);
			    			String data = "/storage/sdcard/bikes.ser";
	
			    			FileInputStream in = new FileInputStream(new File(data));
			    			boolean result = client.storeFile("/bikes.ser", in);
			    			if (result) Log.v("upload result", "succeeded");
			            } catch(Exception ex) {
			            	Log.v("Serialization Save Error : ",ex.getMessage());
			            	ex.printStackTrace();
			            }
			    	}
			    	
			    	editor.putString("serial", serial);
					editor.putString("description", description);
					editor.commit();
			    	
			    	//upload bike photos
			    	client.makeDirectory("/Bike_Pictures/"+prefs.getString("serial","")+"/");
			    	for (int a = 0; a < GridBikeInfo.str.size(); a++){
			    		File f = new File(GridBikeInfo.str.get(a));
			    		client.enterLocalPassiveMode(); // important!
		    			client.setFileType(FTP.BINARY_FILE_TYPE);
		    			FileInputStream in = new FileInputStream(f);
		    			boolean result = client.storeFile("/Bike_Pictures/"+prefs.getString("serial","")+"/picture"+a+".jpg", in);
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
	 * TODO what does this method do?
	 * @param nam - serial number of bike
	 */
	public void has (String nam) {
		System.out.println(bikes.containsKey(nam)); //TODO why am I printing this?
		if (bikes.containsKey(nam)) { //if there is a bike associated with this serial number
			int a = Integer.parseInt(nam.substring(nam.length()-1)); //parse out the last number
			a++; //adds 1 to the number
			serial = nam.substring(0,nam.length()-1)+a; //assigns serial to the new duplicate serial number
			has(serial); //recurses until it reaches a number that hasn't been taken
		} //TODO else if bikes does not contain that key, then shouldn't it add in that duplicate?
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
	} */
	
	/**
	 * TODO why is this premade? what does this do?
	 * @return
	 */
	public HashSet<String> getUri() {
		return uri;	
	}

	/**
	 * TODO why is this premade? what does this do?
	 * @param uri
	 */
	public void setUri(HashSet<String> uri) {
		this.uri = uri;
	}
}

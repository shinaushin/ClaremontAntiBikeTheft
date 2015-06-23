package com.example.claremontantibiketheft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class GridViewTesterBike extends Activity {
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars		
			
	public static final String PREFS_NAME = "MyPrefsFile"; //TODO what does this do?
	public SharedPreferences prefs = null; //TODO what does this do?
	public SharedPreferences.Editor editor = null; //TODO what does this do?
	public ImageAdapter imageAdapter; //TODO what does this do?
	public ArrayList<String> imageUrls = new ArrayList<String>(); //TODO what does this do?
	public HashSet<String> uri = new HashSet<String>(); //TODO what does this do?
	public DisplayImageOptions options; //TODO what does this do?
	protected ImageLoader imageLoader = ImageLoader.getInstance(); //TODO what does this do?
	
	/*
	 * setting up gridview for incident report to display images picked from android gallery]
	 * param - TODO what does a bundle do?
	 */
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); //TODO super to what?
	    setContentView(R.layout.show_android_gallery); //set up a layout with show_android_gallery xml file
	    
	    prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TODO what does this do?
		editor = prefs.edit(); //TODO what does this do?
	    imageAdapter = new ImageAdapter(this, imageUrls); //TODO what does this do?
	    
	    final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID }; //TODO what does this do?
		final String orderBy = MediaStore.Images.Media.DATE_TAKEN; //TODO what does this do?
		
		Cursor imagecursor = managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy + " DESC"); //TODO what does this do?
		
		for (int i = 0; i < imagecursor.getCount(); i++) { //loops through all pictures selected
			imagecursor.moveToPosition(i); //moves to indicated picture
			int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA); //TODO what does this do?
			imageUrls.add(imagecursor.getString(dataColumnIndex)); //TODO what does this do?
		}
		
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.stub_image)
			.showImageForEmptyUri(R.drawable.image_for_empty_url)
			.cacheInMemory()
			.cacheOnDisc()
			.build(); //TODO what does this do?
		
		GridView gridView = (GridView) findViewById(R.id.grid); //sets up a grid with id grid
		gridView.setAdapter(imageAdapter); //TODO what does setting up an adapter do?
	}
	
	/*
	 * method when user presses button after choosing photos for incident report
	 * param: view - TODO what does a view do?
	 */
	public void submit (View view) {
		ArrayList<String> selectedItems = imageAdapter.getCheckedItems(); //sets selectedItems to selected pictures
		Set<String> set = prefs.getStringSet("key", new HashSet<String>()); //var set becomes the value of key
		set.addAll(selectedItems); //add selectedItems into the arraylist set
		editor.putStringSet("key", set); //store key with the value of set
		editor.commit(); //saves the value of key
		uri = (HashSet<String>) prefs.getStringSet("key", null); //sets var uri to value in key
		Intent intent = new Intent(this, RegisterBike.class); //tells program to go to RegisterBike class and setting respective layout associated
		startActivity(intent);
	}
	
	/*
	 * imageAdapter for gridview
	 * TODO what does an imageAdapter do? what is BaseAdapter?
	 */
	public class ImageAdapter extends BaseAdapter {
		
		ArrayList<String> mList; //TODO what does this do?
		LayoutInflater mInflater; //TODO what does a LayoutInflater do?
		Context mContext; //TODO what does a context do?
		SparseBooleanArray mSparseBooleanArray; //TODO what does a SparseBooleanArray
		
		/*
		 * constructor
		 * param: context - TODO what does a context do?
		 * 		  imageList - TODO what does this do?
		 */
		public ImageAdapter(Context context, ArrayList<String> imageList) {
			mContext = context; //sets mContext to context
			mInflater = LayoutInflater.from(mContext); //sets mInflater to a LayoutInflater
			mSparseBooleanArray = new SparseBooleanArray(); //sets mSparseBooleanArray to a new SparseBooleanArray object
			mList = new ArrayList<String>(); //sets mList to a new ArrayList object
			this.mList = imageList; //sets mList to imageList
		}
		
		/*
		 * TODO why is this method premade?
		 */
		public ArrayList<String> getCheckedItems() {
			ArrayList<String> mTempArry = new ArrayList<String>(); //TODO why do I need a temporary arraylist?

			for(int i=0;i<mList.size();i++) { //iterates through all items in mList
				if(mSparseBooleanArray.get(i)) { //TODO what does this do?
					mTempArry.add(mList.get(i)); //adds mList item into mTempArray
				}
			}

			return mTempArry;
		}
		
		/**
		 * returns size of imageUrls
		 */
		@Override
		public int getCount() {
			return imageUrls.size();
		}

		/**
		 * TODO what does this do?
		 */
		@Override
		public Object getItem(int position) {
			return null;
		}

		/**
		 * TODO what does this do?
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * displays the images
		 * param - position: TODO position of what?
		 * 		   convertView: TODO what does this do?
		 * 		   parent: TODO what does this do? what does a ViewGroup do?
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.row_multiphoto_item, null); //TODO what does inflate and its parameters do?
			}

			CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1); //assigns mCheckBox to the checkbox on the layour
			final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1); //TODO what does an ImageView do? And why is it final?
			imageLoader.displayImage("file://"+imageUrls.get(position), imageView, options, new SimpleImageLoadingListener() {
				public void onLoadingComplete(Bitmap loadedImage) {
					Animation anim = AnimationUtils.loadAnimation(GridViewTesterBike.this, R.anim.fade_in);
					imageView.setAnimation(anim);
					anim.start();
				}
			}); //TODO what is an imageLoader, and what does displayImage do?
			
			mCheckBox.setTag(position); //TODO what does setTag do?
			mCheckBox.setChecked(mSparseBooleanArray.get(position)); //TODO what does setChecked do?
			mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener); //TODO what does setOnCheckedChangeListener do? And what is mCheckedChangeListener?
			
			return convertView;
		}
		
		/**
		 * listener for when photo is checked off and selected
		 */
		OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
			}
		}; //TODO what does this OnCheckedChangeListener do?
	}
}
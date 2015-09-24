package com.example.claremontantibiketheft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class SerialSearch extends BaseAdapter{

	//need to clarify difference of static and non-static vars
		//need to clarify difference of private/public vars		
			
		private Context context; //TODO what does this do?
		public SharedPreferences prefs; //TODO what does this do?
		public SharedPreferences.Editor editor; //TODO what does this do?
		public static final String PREFS_NAME = "MyPrefsFile"; //TODO what does this do?
		public HashSet<String> uris; //what do uris do?
		public Iterator<String> iter; //an iterator
		public static ArrayList<String> str = new ArrayList<String>(); //need a more specific var name
		
		//initializes global variables
		public SerialSearch (Context c) {
			context = c; //what does a context do?
			prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //what does this mean along with its params?
			editor = prefs.edit(); //what does the editor do?
			uris = (HashSet<String>) prefs.getStringSet("key3", null); //what is uris and its params?
			if (uris != null) {
				str.clear();
				iter = uris.iterator();
				while (iter.hasNext()) {
					str.add(iter.next());
				}
			}
		}
		
		//TODO why is this premade?
		public int getCount() {
			if (uris == null)
				return 0;
			return uris.size();
	    }
	 
		//TODO why is this premade?
	    public Object getItem(int position) {
	        return null;
	    }
		
	    //TODO why is this premade?
		public long getItemId(int position) {
			return 0;
		}
		
		//displays pictures in gridview for report
		public View getView(int position, View view, ViewGroup parent) {
			ImageView iview; //what does an ImageView do?
			
			if (view == null) {
				iview = new ImageView(context); //TODO what does this do?
				iview.setLayoutParams(new GridView.LayoutParams(150,200)); //TODO what does this do?
				iview.setScaleType(ImageView.ScaleType.CENTER_CROP); //TODO what does this do?
				iview.setPadding(5, 5, 5, 5); //puts a border along the boundary
			} else {
				iview = (ImageView) view; //TODO what does this do?	
			} 
			
			iview.setImageURI(Uri.parse(str.get(position))); //TODO what does this do?
			
			return iview;
		}
}

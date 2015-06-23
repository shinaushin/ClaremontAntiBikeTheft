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

public class GridBikeInfo extends BaseAdapter{
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars
		
	private Context context; //TODO what does a context do?
	public SharedPreferences prefs; //TODO what does prefs do?
	public SharedPreferences.Editor editor; //TODO why do I need an editor?
	public static final String PREFS_NAME = "MyPrefsFile"; //TODO what is this string for?
	public HashSet<String> uris; //TODO what are uris?
	public Iterator<String> iter; //an iterator
	public static ArrayList<String> str = new ArrayList<String>(); //TODO need to make the var name more specific
	
	//initializes all global variables
	public GridBikeInfo(Context c) {
		context = c; //TODO what does a context do?
		prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //TODO what does this do and its params?
		editor = prefs.edit(); //TODO what does an editor do?
		uris = (HashSet<String>) prefs.getStringSet("key", null); //TODO what is uris and its params?
		if (uris != null) {
			str.clear();
			iter = uris.iterator();
			while (iter.hasNext()) {
				str.add(iter.next());
			}
			System.out.println(str);
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
	
	/**
	 * displays pictures in gridview for report
	 * TODO find out what each param does
	 */
	public View getView(int position, View view, ViewGroup parent) {
		ImageView iview; //what does an ImageView do?
		
		if (view == null) {
			iview = new ImageView(context); //TODO what does this do?
			iview.setLayoutParams(new GridView.LayoutParams(150,200)); //TODO what does this do?
			iview.setScaleType(ImageView.ScaleType.CENTER_CROP); //TODO what does this do?
			iview.setPadding(5, 5, 5, 5); //sets a boundary border
		} else {
			iview = (ImageView) view; //TODO what does this do?	
		}
		
		iview.setImageURI(Uri.parse(str.get(position))); //TODO what does this do?
		
		return iview;
	}
}
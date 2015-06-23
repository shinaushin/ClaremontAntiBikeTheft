package com.example.claremontantibiketheft;

import android.app.Activity;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * TODO not sure why this is here
 * @author shinaushin
 *
 */
public abstract class ImageLoaderInstance extends Activity {
	protected ImageLoader imageLoader = ImageLoader.getInstance();	
}

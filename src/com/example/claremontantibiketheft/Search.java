package com.example.claremontantibiketheft;

import java.io.Serializable;
import java.util.Date;

public class Search implements Serializable{

	//ALL THE VARIABLES ASSOCIATED WITH A SEARCH
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars	
		
	private static final long serialVersionUID = -8499141886028599861L; //TODO have no idea why this was generated
	public float lat = 0; //initialize latitude of where search was entered to 0
	public float longitude = 0; //initialize longitude of where search was entered to 0
	public Date date; //date of when search was made
	
	/**
	 * constructor for search
	 * @param a - latitude of where search was entered
	 * @param b - longitude of where search was entered
	 * @param c - date of when search was entered
	 */
	public Search(float a, float b, Date c) { 
		lat = a;
		longitude = b;
		date = c;
	}
}

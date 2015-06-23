package com.example.claremontantibiketheft;
import java.io.Serializable;
import java.util.Date;

public class Bike implements Serializable {

	//ALL VARS ASSOCIATED WITH BIKE OBJECT
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars
	
	private static final long serialVersionUID = -3216063497763279895L; 
	//TODO what this var does
	
	public String serial = ""; //serial number associated with bike
	public String descript = ""; //description of bike
	public boolean isStolen = false; //initialize every bike to being not stolen
	public double latStolen = 0.0; //latitude of where it was stolen initialized to zero
	public double longStolen = 0.0; //longitude of where it was stolen initialized to zero
	
	public Date date = new Date(); //date of when bike was stolen
	//TODO find out what it is initialized to
	
	/**
	 * bike constructor
	 * @param a - serial number of bike
	 * @param d - description of bike
	 * @param b - latitude of where bike was stolen
	 * @param c - longitude of where bike was stolen
	 */
	public Bike (String a, String d, double b, double c) {
		serial = a;
		descript = d;
		latStolen = b;
		longStolen = c;
	}
}


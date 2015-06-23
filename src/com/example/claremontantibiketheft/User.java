package com.example.claremontantibiketheft;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable{

	//ALL VARS ASSOCIATED WITH USER INFORMATION
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars			
		
	private static final long serialVersionUID = -2385108971549710864L; //TODO have no idea why this is generated
	public String name = ""; //name of user
	public String street = ""; //street address
	public String email = ""; //email of user
	public String phone = ""; //phone number of user
	public String facebook = ""; //facebook url of user
	public String gplus = ""; //google plus url of user
	public String twitter = ""; //twitter url of user
	public boolean isReportFinished; //whether or not they finished a report
	public Date reportStart; //date they opened a report
	public Date reportFinish; //date they finished filling out that report
	public Report report; //their incident report
	public ArrayList<Search> searches = new ArrayList<Search>(); //list of their bike searches
	
	/**
	 * constructor of user
	 * @param a - name
	 * @param b - street address
	 * @param c - email address
	 * @param d - phone number
	 * @param e - facebook url
	 * @param f - google plus url
	 * @param g - twitter url
	 */
	public User(String a, String b, String c, String d, String e, String f, String g) {
		name = a;
		street = b;
		email = c;
		phone = d;
		facebook = e;
		gplus = f;
		twitter = g;
	}
}

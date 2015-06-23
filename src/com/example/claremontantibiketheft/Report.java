package com.example.claremontantibiketheft;

import java.io.Serializable;

public class Report implements Serializable{
	
	//ALL THE VARIABLES ASSOCIATED WITH AN UNFINISHED REPORT
	
	//need to clarify difference of static and non-static vars
	//need to clarify difference of private/public vars
		
	public String policeReportNum; //number of police report
	public String policeEmail; //email address of police associated
	public String dateAccident; //date of when the stolen bike incident occurred
	public String timeAccident; //time of when the stolen bike incident occurred
	public String didLock; //whether the bike was locked or not
	public String typeLock; //type of lock that was used to lock the bike
	public String bikeLoc; //location of the bike when stolen
	public String addInfo; //any additional information about the incident
	
	/**
	 * constructor of report
	 * @param num - number of police report
	 * @param email - email address of police associated
	 * @param lock - whether bike was locked or not
	 * @param brand - brand of lock used to lock the bike
	 * @param loc - location of bike when stolen
	 * @param info - any additional information regarding the incident
	 * @param date - date of when the bike was stolen
	 * @param time - time of when the bike was stolen
	 */
	public Report (String num, String email, String lock, String brand, String loc, String info, String date, String time) {
		policeReportNum = num;
		policeEmail = email;
		didLock = lock;
		typeLock = brand;
		bikeLoc = loc;
		addInfo = info;
		dateAccident = date;
		timeAccident = time;
	}
}

package main;
/**
 * Explanation: Implementation of WorkEntry
 * Known Bugs:None
 * Zheng Chu
 * zhengchu@brandeis.edu
 * Nov 7, 2020
 * COSI 21A PA2
 */
/**
 * Represents a WorkEntry object (1 row of the input CSV table).
 * <p><b>You should not edit this file.</b></p>
 * 
 * @author cs21a
 * @version 1.0
 */
public class WorkEntry {
	
	/**
	 * The date that this entry occurred on 
	 */
	private String date;
	
	/**
	 * The time spent on this entry (double that's either a full hour or half an hour to avoid rounding inaccuracy issues) 
	 */
	private double timeSpent;
	
	/**
	 * The activity of this entry 
	 */
	private String activity;
	
	/**
	 * The description of this entry 
	 */
	private String description;
	
	/**
	 * Constructs a WorkEntry object given a Date, time spent, activity, and description 
	 * @param date a date
	 * @param timeSpent an amount of time spent 
	 * @param activity the name of the activity 
	 * @param description the description of the entry 
	 */
	public WorkEntry(String date, double timeSpent, String activity, String description) {
		this.date = date;
		this.timeSpent = timeSpent;
		this.activity = activity;
		this.description = description;
	}

	/**
	 * Gets the date of this entry 
	 * @return entry date 
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Gets the time spent on this entry
	 * @return entry time spent 
	 */
	public double getTimeSpent() {
		return timeSpent;
	}

	/**
	 * Gets the activity of this entry
	 * @return entry activity 
	 */
	public String getActivity() {
		return activity;	
	}
	

	/**
	 * Gets the description of this entry 
	 * @return entry description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the String representation of a WorkEntry
	 * @return something like "[12/19/2019] Worked on PA (4 h): wrote JUnit tests" 
	 */
	@Override
	public String toString() {
		return String.format("[%s] %s (%.1f h): %s", date.toString(), activity, timeSpent, description).trim();
	}
	
}


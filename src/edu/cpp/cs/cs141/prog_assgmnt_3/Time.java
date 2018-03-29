/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #3
 *
 * This assignment creates a program that would be used by a veterinary office. The veterinary office attends to dogs, birds and fish,
 * as well as five different types for each of those three animals. This program will then be able to list out all of the appropriate
 * information on the animals, their owners and their respective appointments. Since the office can be dealing with large amounts of
 * data, this program is to be able to search through the lists that this program can display and shorten it to match whatever specification
 * the user desires. This program is to also be able to save to a file and restore the data from that file even after the program is terminated.
 *
 * Joel Tengco
 */
package edu.cpp.cs.cs141.prog_assgmnt_3;

import java.io.Serializable;

/**
 * This class represents a time. It is represented by values for an hour and for a minute of
 * the {@code Time} object. Objects of this class will only consist of valid times, in other words,
 * an object of this class cannot be created with an invalid hour and/or minute value.
 * @author Joel Tengco
 */
public class Time implements Comparable<Time>, Serializable {
	/**
	 * The ID for this class to be serializable.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The hour of this time, being within the interval [0, 23].
	 */
	private int hour;
	/**
	 * The minute of this time, being within the interval [0, 59].
	 */
	private int minute;
	
	/**
	 * Creates a new {@code Time} object with a default value of 0 for both
	 * hour and minute. Thus, the time of this newly created object is 12:00 AM.
	 */
	public Time() {
		hour = 0;
		minute = 0;
	}
	
	/**
	 * Creates a new {@code Time} object with an hour and minute value specified by
	 * the arguments given. The arguments are checked for validity before creating the new object.
	 * @param hour The hour of this time, must be within [0, 23].
	 * @param minute The minute of this time, must be within [0, 59].
	 * @throws IllegalArgumentException Whenever an invalid integer is given for hour and/or minute.
	 * In other words, hour and/or minute is/are not within their respective intervals.
	 */
	public Time(int hour, int minute) throws IllegalArgumentException {
		if(hour >= 0 && hour < 24)
			this.hour = hour;
		else
			throw new IllegalArgumentException("ERROR: invalid hour given, enter a number within [0, 23]");
		
		if(minute >= 0 && minute < 60)
			this.minute = minute;
		else
			throw new IllegalArgumentException("ERROR: invalid minute given, enter a number within [0, 59]");
	}
	
	/**
	 * Creates a new {@code Time} object whose hour and minute values are copied from the hour and minute
	 * from the given argument.
	 * @param otherTime The {@code Time} object in which to copy the hour and minute from.
	 */
	public Time(Time otherTime) {
		hour = otherTime.hour;
		minute = otherTime.minute;
	}
	
	/**
	 * Sets a new hour for this time.
	 * @param hour The new hour for this time. Must be within [0, 23].
	 * @throws IllegalArgumentException When the new hour given is not within the interval [0, 23].
	 */
	public void setHour(int hour) throws IllegalArgumentException {
		if(hour >= 0 && hour < 24)
			this.hour = hour;
		else
			throw new IllegalArgumentException("ERROR: invalid hour given, enter a number within [0, 23]");
	}
	
	/**
	 * Sets a new minute for this time.
	 * @param minute The new minute for this time. Must be within [0, 59].
	 * @throws IllegalArgumentException When the new minute given is not within [0, 59].
	 */
	public void setMinute(int minute) throws IllegalArgumentException {
		if(minute >= 0 && minute < 60)
			this.minute = minute;
		else
			throw new IllegalArgumentException("ERROR: invalid minute given, enter a number within [0, 59]");
	}
	
	/**
	 * Gets a string representing this time, in the format: HH:MM mm such that HH is the hour of this time
	 * from 1 to 12, MM is the minute of this time from 0 to 59, and mm is the meridiem of this time either
	 * being AM or PM. The hour of this time determines its meridiem, where an hour within [0, 11] implies AM
	 * and an hour within [12, 23] implies PM.
	 */
	public String toString() {
		String time;
		String meridiem;
		
		if(hour % 12 == 0)
			time = "12";
		else
			time = Integer.toString(hour % 12);
		
		if(hour >= 12)
			meridiem = "PM";
		else
			meridiem = "AM";
		
		time = time.concat(":" + String.format("%02d", minute) + " " + meridiem);
		return time;
	}
	
	/**
	 * Gets a copy of this time.
	 * @return A reference to a newly created {@code Time} object that contains a time
	 * equivalent to this time.
	 */
	public Time getCopy() {
		Time temp = new Time(this);
		return temp;
	}
	
	/**
	 * Compares this time with the one given in the argument.
	 * @param otherTime The other time to compare with.
	 * @return An integer less than 0 if this time is earlier than the one given,
	 * greater than 0 if this time is later than the one given, and the integer 0
	 * itself if both times are equivalent, having the same hour and minute values.
	 */
	public int compareTo(Time otherTime) {
		if(hour != otherTime.hour)
			return hour - otherTime.hour;
		else
			return minute - otherTime.minute;
	}
}

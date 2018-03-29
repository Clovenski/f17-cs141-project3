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
 * This class represents a date. It is represented by values for a month, day and year.
 * This class also throws an exception when an invalid date is trying to be created using this
 * class. It is also designed to determine if the year is a leap year or not, thus determining
 * if the upper limit of a day in the month of February for that year is either 28 or 29. The
 * upper limit is also determined for all the other months. Therefore, objects of this class
 * will always represent a valid date.
 * @author Joel Tengco
 */
public class Date implements Comparable<Date>, Serializable {
	/**
	 * The ID for this class to be serializable.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The month of this date, being within the interval [1, 12].
	 */
	private int month;
	/**
	 * The day of this date, being within [1, n] such that n is the
	 * upper limit of a valid day, depending on this date's month.
	 */
	private int day;
	/**
	 * The year of this date, always being a positive integer.
	 */
	private int year;
	/**
	 * Determines if this date is within a leap year, thus a true value represents
	 * this date's year is a leap year, false otherwise.
	 */
	private boolean isLeapYear;
	/**
	 * An array of strings that represents the full names of the months in a year.
	 * This is mainly used to help with {@linkplain #getFormalDate()}.
	 */
	private final String[] MONTHS = {"January", "February", "March", "April", "May",
										"June", "July", "August", "September", "October",
										"November", "December"};
	
	/**
	 * Creates a new {@code Date} object with default information, with a month of 1,
	 * day of 1 and year of 1, thus being a date not within a leap year.
	 */
	public Date() {
		month = 1;
		day = 1;
		year = 1;
		isLeapYear = false;
	}
	
	/**
	 * Creates a new {@code Date} object with information specified by the arguments.
	 * This constructor throws an exception whenever an invalid date is given. The year
	 * must always be positive, the month must be between 1 and 12 inclusive, and the day
	 * must be greater than zero and less than or equal to the upper limit of a valid day
	 * depending on the month and year given.
	 * @param month The month of this date, within [1, 12]
	 * @param day The day of this date, within [1, n] such that n is the last day of this date's month.
	 * @param year The year of this date, which must always be positive.
	 * @throws IllegalArgumentException When an invalid date is given, whether it be the month, day, and/or
	 * year that is invalid.
	 */
	public Date(int month, int day, int year) throws IllegalArgumentException {
		int dayUpperLimit;
		isLeapYear = false;
		
		if(month >= 1 && month <= 12)
			this.month = month;
		else
			throw new IllegalArgumentException("ERROR: invalid month given, enter a number within [1, 12]");
		
		if(year < 0)
			throw new IllegalArgumentException("ERROR: invalid year given, enter a positive number");
		else
			this.year = year;
		
		if(month <= 7) {
			if(month == 2) {
				if(year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
					isLeapYear = true;
				dayUpperLimit = isLeapYear ? 29 : 28;
			} else
				dayUpperLimit = month % 2 == 1 ? 31 : 30;
		} else
			dayUpperLimit = month % 2 == 1 ? 30: 31;
		
		if(day >= 1 && day <= dayUpperLimit)
			this.day = day;
		else
			throw new IllegalArgumentException("ERROR: invalid day for " + MONTHS[month - 1] + ", enter a number within [1, " + dayUpperLimit + "]");
	}
	
	/**
	 * Copy constructor for this class. The newly created {@code Date} object's information is copied from the
	 * given {@code Date} object's information.
	 * @param date The {@code Date} object to copy this new date from.
	 */
	public Date(Date date) {
		month = date.month;
		day = date.day;
		year = date.year;
		isLeapYear = date.isLeapYear;
	}
	
	/**
	 * Sets a new month for this date if the given argument is valid.
	 * @param month The new month for this date.
	 * @throws IllegalArgumentException When the given month is not within [1, 12] or the
	 * new month value for this date will cause it to be invalid from the day being greater
	 * than the upper limit of the new month.
	 */
	public void setMonth(int month) throws IllegalArgumentException {
		int dayUpperLimit;
		
		if(month > 12 || month < 1)
			throw new IllegalArgumentException("ERROR: invalid month given, enter a number within [1, 12]");
		
		if(day >= 29) {
			if(month <= 7) {
				if(month == 2)
					dayUpperLimit = isLeapYear ? 29 : 28;
				else
					dayUpperLimit = month % 2 == 1 ? 31 : 30;
			} else
				dayUpperLimit = month % 2 == 1 ? 30: 31;
			
			if(day > dayUpperLimit)
				throw new IllegalArgumentException("ERROR: invalid month given, this date's current day will become invalid with the given month");
		}
		
		this.month = month;
	}
	
	/**
	 * Sets a new day for this date if the given argument is valid.
	 * @param day The new day for this date.
	 * @throws IllegalArgumentException When an integer not within [1, 31] is given as an
	 * argument, or is greater than the upper limit of the currently assigned month.
	 */
	public void setDay(int day) throws IllegalArgumentException {
		int dayUpperLimit;
		
		if(day < 1 || day > 31)
			throw new IllegalArgumentException("ERROR: invalid day given");
		
		if(month <= 7) {
			if(month == 2)
				dayUpperLimit = isLeapYear ? 29 : 28;
			else
				dayUpperLimit = month % 2 == 1 ? 31 : 30;
		} else
			dayUpperLimit = month % 2 == 1 ? 30: 31;
		
		if(day > dayUpperLimit)
			throw new IllegalArgumentException("ERROR: invalid day for " + MONTHS[month - 1] + ", enter a number within [1, " + dayUpperLimit + "]");
		
		this.day = day;
	}
	
	/**
	 * Sets a new year for this date if the given argument is valid.
	 * @param year The new year for this date.
	 * @throws IllegalArgumentException When a negative integer is given for the argument, or
	 * when the current date happens to be 29 February [any leap year] and the new year for this
	 * date is not a leap year, thus making the new date an invalid date.
	 */
	public void setYear(int year) throws IllegalArgumentException {
		if(year < 0)
			throw new IllegalArgumentException("ERROR: invalid year given, enter a positive number");
		
		if(year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
			isLeapYear = true;
		else {
			isLeapYear = false;
			if(month == 2 && day == 29)
				throw new IllegalArgumentException("ERROR: invalid year given, this date's current day will become invalid with the given year");
		}
		
		this.year = year;
	}
	
	/**
	 * Gets a string that formally represents this date. For example, 21 January 2019.
	 * @return A string of the format: [day] [full month name] [year]
	 */
	public String getFormalDate() {
		String date;
		
		date = Integer.toString(day) + " " + MONTHS[month - 1] + " " + Integer.toString(year);
		return date;
	}
	
	/**
	 * Gets a string that represents this date. For example, 1-21-2019.
	 * @return A string of the format: [month number]-[day]-[year]
	 */
	public String toString() {
		String date;
		
		date = Integer.toString(month) + "-" + Integer.toString(day) + "-" + Integer.toString(year);
		return date;
	}
	
	/**
	 * Creates a new {@code Date} object with a date that is equivalent to this date.
	 * A reference to this newly created object is then returned.
	 * @return A reference to a {@code Date} object that contains a date equivalent to this date.
	 */
	public Date getCopy() {
		Date temp = new Date(this);
		return temp;
	}
	
	/**
	 * Compares this date with the date given as an argument.
	 * @param otherDate The other date to compare this date with.
	 * @return An integer less than 0 if this date is earlier than the one given,
	 * greater than 0 if this date is later than the one given, and the integer 0
	 * itself if both this date and the one given have the same month/day/year.
	 */
	public int compareTo(Date otherDate) {
		if(year != otherDate.year)
			return year - otherDate.year;
		
		if(month != otherDate.month)
			return month - otherDate.month;
		
		if(day != otherDate.day)
			return day - otherDate.day;
		
		// Both dates have the same month-day-year
		return 0;
	}
}

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
 * This is the class that represents the appointments in the program. It contains
 * properties of an appointment's involved client, as well as its date and time. Each
 * object of this class also has a status value which represents whether the appointment
 * is either resolved or outstanding.
 * @author Joel Tengco
 */
public class Appointment implements Comparable<Appointment>, Serializable {
	/**
	 * The auto-generated ID for this class to become serializable.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A reference to the involved {@code Client} object for this appointment.
	 */
	private Client client;
	/**
	 * The date of this appointment.
	 */
	private Date date;
	/**
	 * The time of this appointment.
	 */
	private Time time;
	/**
	 * A boolean value representing if this appointment is currently resolved.
	 * A value of true represents this appointment is resolved.
	 */
	private boolean isResolved;
	
	/**
	 * Creates a new {@code Appointment} object with default information except for
	 * this appointment's involved client, in which the given argument is what is set
	 * for that value. This constructor is mainly used for when the program aims to
	 * compare two appointments by their clients. The objects instantiated using this
	 * constructor are objects containing the target client to be compared with. This
	 * eases the creation of temporary {@code Appointment} target objects, where the
	 * only value that matters is the client value.
	 * @param client The {@code Client} object that this appointment is involving.
	 */
	public Appointment(Client client) {
		this.client = client;
		date = new Date();
		time = new Time();
		isResolved = false;
	}
	
	/**
	 * Creates a new {@code Appointment} object with default information except for this
	 * appointment's date value, in which the given argument is what is set for that value.
	 * This constructor is mainly used for when the program aims to compare two appointments
	 * by their dates. The objects instantiated using this constructor are objects containing
	 * the target date value to be compared with.
	 * @param date The date of this appointment.
	 */
	public Appointment(Date date) {
		client = new Client();
		this.date = date;
		time = new Time();
		isResolved = false;
	}
	
	/**
	 * Creates a new {@code Appointment} object with information according to the given
	 * arguments, such as the {@code Client} object involved and the date and time for this
	 * appointment. This new appointment is also instantiated as an unresolved appointment,
	 * in other words it has a status of outstanding.
	 * @param client The client that this appointment involves.
	 * @param date The date of this appointment.
	 * @param time The time of this appointment.
	 */
	public Appointment(Client client, Date date, Time time) {
		this.client = client;
		this.date = date;
		this.time = time;
		isResolved = false;
	}
	
	/**
	 * Sets this appointment's status to resolved instead of outstanding. An already resolved
	 * appointment stays resolved if this method is invoked in that case.
	 */
	public void setAsResolved() {
		isResolved = true;
	}
	
	/**
	 * Gets the current status of this appointment, being either resolved or outstanding.
	 * @return True if this appointment has a status of resolved, false otherwise.
	 */
	public boolean isResolved() {
		return isResolved;
	}
	
	/**
	 * Gets the {@code Client} object that is involved in this appointment.
	 * @return A reference to this appointment's involved {@code Client} object.
	 */
	public Client getClient() {
		return client;
	}
	
	/**
	 * Gets the name of this appointment's involved client.
	 * @return A string of this appointment's client's name.
	 */
	public String getClientName() {
		return client.getName();
	}
	
	/**
	 * Gets a deep copy of this appointment's date. In other words, any modifications
	 * done to the returned reference does not affect this appointment in any way.
	 * @return A reference to a {@code Date} object consisting of a value that is
	 * equivalent to this appointment's own date.
	 */
	public Date getDate() {
		return date.getCopy();
	}
	
	/**
	 * Gets a deep copy of this appointment's time. In other words, any modifications
	 * done to the returned reference does not affect this appointment in any way.
	 * @return A reference to a {@code Time} object consisting of a value that is
	 * equivalent to this appointment's own time.
	 */
	public Time getTime() {
		return time.getCopy();
	}
	
	/**
	 * Gets a formatted string that consists of this appointment's information, such as the
	 * client involved, its date and its time. Each information is separated by "//" in this
	 * string.
	 * @return A string formatted in a way that represents this appointments information.
	 */
	public String toString() {
		String appointmentInfo;
		
		appointmentInfo = String.format("%-20s // %-17s // %8s", client.getName(), date.getFormalDate(), time.toString());
		
		return appointmentInfo;
	}
	
	/**
	 * Compares this appointment's date and time with the given appointment's date and time.
	 * @param otherAppointment The other appointment to compare this appointment with.
	 * @return An integer less than 0 if this appointment is earlier than the given
	 * appointment, greater than 0 if it is later, and the integer 0 itself if their dates
	 * and time are equivalent.
	 */
	public int compareTo(Appointment otherAppointment) {
		int compareValue = date.compareTo(otherAppointment.getDate());
		if(compareValue ==  0)
			return time.compareTo(otherAppointment.getTime());
		else
			return compareValue;
	}
}

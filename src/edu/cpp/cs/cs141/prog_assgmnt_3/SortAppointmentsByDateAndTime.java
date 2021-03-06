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

import java.util.Comparator;

/**
 * This class is solely used to sort an appointment list by both date and time. It compares
 * two given appointments using their compareTo method.
 * @author Joel Tengco
 */
public class SortAppointmentsByDateAndTime implements Comparator<Appointment> {
	/**
	 * Compares two given appointments according to both their date and their time.
	 * @param app1 One of the {@code Appointment} objects to be compared.
	 * @param app2 One of the {@code Appointment} objects to be compared.
	 * @return An integer less than 0 if app1 is earlier than app2, greater than 0 if it is later,
	 * and the integer 0 itself if they are equivalent in both date and time.
	 */
	public int compare(Appointment app1, Appointment app2) {
		return app1.compareTo(app2);
	}
}

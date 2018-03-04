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
 * This class implements comparing two {@code Appointment} objects by their date.
 * @author Joel Tengco
 */
public class AppointmentByDate implements Comparator<Appointment> {
	/**
	 * Compares two appointments by their date.
	 * @param apptmnt1 One of the appointments to compare.
	 * @param apptmnt2 One of the appointments to compare.
	 * @return An integer less than 0 if apptmnt1's date is earlier than apptmnt2's date,
	 * greater than 0 if it is later, and the integer 0 itself if they are equivalent.
	 */
	public int compare(Appointment apptmnt1, Appointment apptmnt2) {
		return apptmnt1.getDate().compareTo(apptmnt2.getDate());
	}
}

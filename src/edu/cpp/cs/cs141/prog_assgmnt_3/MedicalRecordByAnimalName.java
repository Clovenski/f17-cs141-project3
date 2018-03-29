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
 * This class implements the comparison of two animals by their names.
 * @author Joel Tengco
 */
public class MedicalRecordByAnimalName implements Comparator<Animal> {
	/**
	 * Compares the animal names of the two {@code Animal} objects given.
	 * @param a1 One of the animals to compare.
	 * @param a2 One of the animals to compare.
	 * @return The integer 0 if and only if the two animals' names are equivalent,
	 * ignoring case, otherwise an integer != 0 according to {@linkplain String#compareTo(String)}.
	 */
	public int compare(Animal a1, Animal a2) {
		return a1.getName().compareToIgnoreCase(a2.getName());
	}
}

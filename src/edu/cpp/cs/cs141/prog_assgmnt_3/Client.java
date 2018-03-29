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
 * This class represents the clients that are going to be involved in the program. It contains
 * information such as the client's name, address and phone number.
 * @author Joel Tengco
 */
public class Client implements Serializable {
	/**
	 * The ID in order for this class to be serializable.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The name of this client. No restrictions on this value, any form of a string is accepted
	 * for this field.
	 */
	private String name;
	/**
	 * The address of this client. No restrictions on this value, any form of a string is accepted
	 * for this field.
	 */
	private String address;
	/**
	 * A long that represents the phone number of the client. A valid value of this field will always
	 * be between 2002000000 and 9999999999, inclusive.
	 */
	private long phoneNumber;
	
	/**
	 * Creates a default {@code Client} object with empty strings for the client's name and address,
	 * and a phone number of 0.
	 */
	public Client() {
		name = "";
		address = "";
		phoneNumber = 0;
	}
	
	/**
	 * Creates a new {@code Client} object with default values except for its name, being assigned
	 * what is given as an argument.
	 * @param name The name of this client.
	 */
	public Client(String name) {
		this.name = name;
		address = "";
		phoneNumber = 0;
	}
	
	/**
	 * Creates a new {@code Client} object with the given argument values. The long representing
	 * this client's phone number must be between 2002000000 and 9999999999, inclusive.
	 * @param name The name of this client.
	 * @param address The address of this client.
	 * @param phoneNumber The phone number of this client.
	 * @throws IllegalArgumentException When the long given for this client's phone number
	 * is invalid, being less than 2002000000 or greater than 9999999999.
	 */
	public Client(String name, String address, long phoneNumber) throws IllegalArgumentException {
		this.name = name;
		this.address = address;
		
						/*999-999-9999				200-200-0000*/
		if(phoneNumber > 9999999999L || phoneNumber < 2002000000)
			throw new IllegalArgumentException("ERROR: invalid phone number, please enter a valid phone number");
		else
			this.phoneNumber = phoneNumber;
	}
	
	/**
	 * Gets the name of this client.
	 * @return A string representing this client's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the address of this client.
	 * @return A string representing this client's address.
	 */
	public String getAdress() {
		return address;
	}
	
	/**
	 * Gets this client's phone number, formatted in a way such that the numbers are
	 * easily distinguishable. For example: ###-###-####
	 * @return A string formatted as: ###-###-#### such that the sequence of #'s represent
	 * this client's phone number.
	 */
	public String getNumber() {
		String number;
		
		number = Long.toString(phoneNumber);
		number = number.substring(0, 3) + "-" + number.substring(3, 6) + "-" + number.substring(6, 10);
		return number;
	}
	
	/**
	 * Gets a string representing this client's information. This string is formatted as:
	 * [name] // [phone number] // [address]
	 * @return A formatted string representing this client's information.
	 */
	public String toString() {
		String clientInfo;
		
		clientInfo = String.format("%-20s // %-12s // %s", name, this.getNumber(), address);
		
		return clientInfo;
	}
}

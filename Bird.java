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

/**
 * This is one of the three animals that the veterinary office attends to. It inherits
 * from the class {@linkplain Animal} and properly implements the animal's type, in which
 * this animal's kind is "Bird" and the five different types of bird are in {@linkplain BirdType}.
 * @author Joel Tengco
 */
public class Bird extends Animal {
	/**
	 * The ID for this class to be serializable.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The five different types of bird that objects of type {@linkplain Bird} should be.
	 * @author Joel Tengco
	 */
	protected static enum BirdType{PARROT, HUMMINGBIRD, OWL, PENGUIN, HERON};
	
	/**
	 * Creates a new default {@code Bird} object with default information.
	 */
	public Bird() {
		super();
	}
	
	/**
	 * Creates a new {@code Bird} object with information according to the given arguments.
	 * <p>
	 * The string argument named {@code type} should be one of the values from {@linkplain BirdType},
	 * case insensitive as the type of this animal will always be in lower case. 
	 * @param name The name of this bird, given by its owner.
	 * @param age The age of this bird in years.
	 * @param owner A reference to this bird's owner.
	 * @param type The type of bird this animal is.
	 */
	public Bird(String name, int age, Client owner, String type) {
		super(name, age, owner, type.toLowerCase());
	}
}

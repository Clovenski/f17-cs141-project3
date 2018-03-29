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
 * This class represents one of the three animals that the veterinary office attends to.
 * The name of this class, "Dog," represents the kind of the animal. The five different types
 * of dog are defined in {@linkplain DogType}.
 * @author Joel Tengco
 */
public class Dog extends Animal {
	/**
	 * The ID for this class to be serializable.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The five different types that a dog can be within the program.
	 * @author Joel Tengco
	 */
	protected static enum DogType{POODLE, BULLDOG, BEAGLE, ROTTWEILER, HUSKY};
	
	/**
	 * Creates a new {@code Dog} object with default values according to {@linkplain Animal#Animal()}.
	 */
	public Dog() {
		super();
	}
	
	/**
	 * Creates a new {@code Dog} object with information specified by the given arguments.
	 * The argument named {@code type} should be one of the values in {@linkplain DogType},
	 * case insensitive since the type of this animal will always be in lower case.
	 * @param name The name of this dog, given by the owner.
	 * @param age The age of this dog, in years.
	 * @param owner A reference to the owner of this dog.
	 * @param type The type of this dog.
	 */
	public Dog(String name, int age, Client owner, String type) {
		super(name , age, owner, type.toLowerCase());
	}
}

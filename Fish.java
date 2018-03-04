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
 * This class represents one of the three animals that the program attends to. The name of
 * this class, "Fish," is the kind of the animal, with the type of the animal being one of the
 * values defined in {@linkplain FishType}.
 * @author Joel Tengco
 */
public class Fish extends Animal {
	/**
	 * The ID for this class to be serializable.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The five different type of fishes that can be in the program.
	 * @author Joel Tengco
	 */
	protected static enum FishType{SALMON, TUNA, GUPPY, BLUEGILL, SWORDFISH};
	
	/**
	 * Creates a new {@code Fish} object with default values according to {@linkplain Animal#Animal()}.
	 */
	public Fish() {
		super();
	}
	
	/**
	 * Creates a new {@code Fish} object with information according to the arguments given.
	 * The argument named type should be one of the values defined in {@linkplain FishType},
	 * case insensitive since the type of this animal will always be in lower case.
	 * @param name The name of this fish, given by its owner.
	 * @param age The age of this fish, in years.
	 * @param owner A reference to the owner of this fish.
	 * @param type The type of this fish.
	 */
	public Fish(String name, int age, Client owner, String type) {
		super(name, age, owner, type.toLowerCase());
	}
}

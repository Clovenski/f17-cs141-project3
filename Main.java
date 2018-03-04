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
 * The class that contains the main method that demonstrates the {@linkplain AdminProgram}.
 * Running the main method in here, passing it a first argument of "-test" further demonstrates
 * the program with loaded test data. Please see {@linkplain Tester} to see how the test data is
 * generated.
 * @author Joel Tengco
 */
public class Main {

	/**
	 * The main method that demonstrates this whole assignment.
	 * @param args A first argument in this array equivalent to "-test"
	 * demonstrates the program with test data loaded, otherwise the program
	 * is loaded with the save file pertaining to the program, if it exists.
	 */
	public static void main(String[] args) {
		AdminProgram program = null;
		
		if(args.length == 0)
			program = new AdminProgram();
		else if(args[0].equals("-test"))
			program = new AdminProgram(new Tester());
		else
			program = new AdminProgram();
		
		program.start();
	}

}

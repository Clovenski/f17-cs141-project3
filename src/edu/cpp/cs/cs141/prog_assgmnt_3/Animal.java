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
import java.util.ArrayList;

/**
 * This class is the super class to the three different animals that the veterinary
 * office attends to. It captures the name of the animal, its age, its owner and its
 * animal type. It also contains information on the animal's past and current diseases,
 * vaccinations and currently associated appointments.
 * @author Joel Tengco
 */
public class Animal implements Serializable {
	/**
	 * An auto-generated ID for this class in order for it to be serializable.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The name of the animal, given by its owner.
	 */
	private String name;
	/**
	 * The age of this animal in years.
	 */
	private int age;
	/**
	 * A reference to the owner of this animal.
	 */
	private Client owner;
	/**
	 * The animal type of this animal. The type of the animal and the kind of
	 * the animal are treated as two different values, where its type is more
	 * specific than the kind. In other words, the type of this animal depends
	 * on its "kind," in which the name of this class's subclasses represent
	 * this value.
	 */
	private String type;
	/**
	 * A list of strings that represent all of this animal's past diseases.
	 */
	private ArrayList<String> pastDiseases;
	/**
	 * A list of strings that represent all of this animal's current diseases.
	 */
	private ArrayList<String> currentDiseases;
	/**
	 * A list of strings that represent all of this animal's vaccinations.
	 */
	private ArrayList<String> vaccinations;
	/**
	 * A list of {@code Appointment} objects that this animal is associated with.
	 */
	private ArrayList<Appointment> appointments;
	
	/**
	 * Creates a new {@code Animal} object with empty information. The newly created
	 * object has a name of "", an age of 0, no owner, and a type of "".
	 */
	public Animal() {
		name = "";
		age = 0;
		owner = null;
		type = "";
	}
	
	/**
	 * Creates a new {@code Animal} object with empty information except for its owner
	 * value. All of its information is exactly how it would be if instantiated with
	 * {@linkplain #Animal()}, except the reference to this animal's owner is the one
	 * that is given. This constructor was created solely to make a target {@code Animal}
	 * object that would be used to compare with the animals contained in a list, in which the
	 * list would be shortened to contain the animals that match this target's owner reference.
	 * @param owner A reference to this animal's owner.
	 */
	public Animal(Client owner) {
		name = "";
		age = 0;
		this.owner = owner;
		type = "";
	}
	
	/**
	 * Creates a new {@code Animal} object with empty information except for the animal's
	 * name. All of its information is exactly how it would be if instantiated with
	 * {@linkplain #Animal()}, except the name of this animal will be the given string
	 * argument. This constructor was created solely to make a target {@code Animal} object
	 * that would be used to compare with the animals contained in a list, in which the list
	 * would be shortened to contain the animals that match this target's animal name.
	 * @param name The name of this animal.
	 */
	public Animal(String name) {
		this.name = name;
		age = 0;
		owner = null;
		type = "";
	}
	
	/**
	 * Creates a new {@code Animal} object with complete information according to the
	 * arguments given. The name of this animal, its age, its owner and its type will
	 * be assigned based on the values given in the appropriate argument. This is the
	 * main constructor used in the program to properly add an animal to the data
	 * registry.
	 * @param name The name of this animal, given by its owner.
	 * @param age The age of this animal in years.
	 * @param owner A reference to this animal's owner.
	 * @param type The type of this animal, which is mainly used as a specifier.
	 */
	public Animal(String name, int age, Client owner, String type) {
		this.name = name;
		this.age = age;
		this.owner = owner;
		this.type = type;
		pastDiseases = new ArrayList<String>();
		currentDiseases = new ArrayList<String>();
		vaccinations = new ArrayList<String>();
		appointments = new ArrayList<Appointment>();
	}
	
	/**
	 * Adds the given string to this animal's list of past diseases.
	 * @param diseaseName A string representing one of this animal's past diseases.
	 */
	public void addPastDisease(String diseaseName) {
		if(!diseaseName.equals(""))
			pastDiseases.add(diseaseName);
	}
	
	/**
	 * Adds the given string to this animal's list of current diseases.
	 * @param diseaseName A string representing one of this animal's current diseases.
	 */
	public void addCurrentDisease(String diseaseName) {
		if(!diseaseName.equals(""))
			currentDiseases.add(diseaseName);
	}
	
	/**
	 * Adds the given string to this animal's list of vaccinations.
	 * @param vaccinationName A string representing one of this animal's vaccinations.
	 */
	public void addVaccination(String vaccinationName) {
		if(!vaccinationName.equals(""))
			vaccinations.add(vaccinationName);
	}
	
	/**
	 * Adds a new {@code Appointment} object to this animal's list of currently associated
	 * appointments.
	 * @param newAppointment The new appointment that this animal is now associated with.
	 */
	public void addAppointment(Appointment newAppointment) {
		appointments.add(newAppointment);
	}
	
	/**
	 * Gets the name of this animal, given by its owner.
	 * @return A string representing this animal's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the age of this animal, in years.
	 * @return The age of this animal in years.
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Gets the owner of this animal.
	 * @return A reference to this animal's owner.
	 */
	public Client getOwner() {
		return owner;
	}
	
	/**
	 * Gets this animal's owner's name.
	 * @return A string representing this animal's owner's name.
	 */
	public String getOwnerName() {
		return owner.getName();
	}
	
	/**
	 * Gets this animal's type, as in a specifier to this animal's kind.
	 * @return A string representing this animal's type.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets a string in the form of a list of this animal's past diseases. If this animal
	 * does not have any past diseases, then the string "None" is returned, preceded and
	 * succeeded by new line characters. Otherwise, every past disease of this animal is
	 * represented in the returned string in one line each. The list is also preceded and
	 * succeeded by new line characters.
	 * @return A string in the form of a list of this animal's past diseases. The list itself
	 * is preceded and succeeded by a new line character.
	 */
	public String getPastDiseases() {
		String output = "";
		
		if(pastDiseases.size() == 0)
			output = "None\n";
		
		for(String disease : pastDiseases)
			output = output.concat(disease) + "\n";
		
		return String.format("\n%s\n", output);
	}
	
	/**
	 * Gets a string in the form of a list of this animal's current diseases. If this animal
	 * does not have any current diseases, then the string "None" is returned, preceded and
	 * succeeded by new line characters. Otherwise, every current disease of this animal is
	 * represented in the returned string in one line each. The list is also preceded and
	 * succeeded by new line characters.
	 * @return A string in the form of a list of this animal's current diseases. The list
	 * itself is preceded and succeeded by new line characters.
	 */
	public String getCurrentDiseases() {
		String output = "";
		
		if(currentDiseases.size() == 0)
			output = "None\n";
		
		for(String disease : currentDiseases)
			output = output.concat(disease) + "\n";
		
		return String.format("\n%s\n", output);
	}
	
	/**
	 * Gets a string in the form of a list of this animal's vaccinations. If this animal
	 * does not have any vaccinations, then the string "None" is returned, preceded and
	 * succeeded by new line characters. Otherwise, every vaccination of this animal is
	 * represented in the returned string in one line each. The list is also preceded and
	 * succeeded by new line characters.
	 * @return A string in the form of a list of this animal's vaccinations. The list
	 * itself is preceded and succeeded by new line characters.
	 */
	public String getVaccinations() {
		String output = "";
		
		if(vaccinations.size() == 0)
			output = "None\n";
		
		for(String vaccination : vaccinations)
			output = output.concat(vaccination) + "\n";
		
		return String.format("\n%s\n", output);
	}
	
	/**
	 * Gets a reference to this animal's appointments list. The returned list consists
	 * of all of this animal's associated appointments. Any modifications to the returned
	 * list directly affects this animal's own list. In other words, a shallow copy is given.
	 * @return A reference to this animal's list of appointments.
	 */
	public ArrayList<Appointment> getAppointmentsList() {
		return appointments;
	}
	
	/**
	 * Gets a string in the form of a list of this animal's current appointments. If this animal
	 * does not have any current appointments, then the string "None" is returned, preceded and
	 * succeeded by new line characters. Otherwise, every current appointment of this animal is
	 * represented in the returned string in one line each. The list is also preceded and
	 * succeeded by new line characters.
	 * @return A string in the form of a list of this animal's currently associated appointments.
	 * The list itself is preceded and succeeded by new line characters.
	 */
	public String getAppointments() {
		String output = "";
		
		if(appointments.size() == 0)
			output = "None\n";
		
		for(Appointment appointment : appointments)
			if(!appointment.isResolved())
				output = output.concat(appointment.toString()) + "\n";
		
		return String.format("\n%s\n", output);
	}
	
	/**
	 * Gets a formatted string that can properly display all of this animal's complete
	 * information, including everything that would be returned in {@linkplain #toString()}
	 * as well as lists containing this animal's past and current diseases, vaccinations
	 * and currently associated appointments.
	 * @return A string that represents all of this animal's information, formatted in a way
	 * that is informative enough to be displayed onto the screen.
	 */
	public String getFullInfo() {
		String finalString;
		
		finalString = this.toString() + "\n";
		finalString = finalString.concat("\nPast Diseases:\n" + getPastDiseases());
		finalString = finalString.concat("\nCurrent Diseases:\n" + getCurrentDiseases());
		finalString = finalString.concat("\nVaccinations:\n" + getVaccinations());
		finalString = finalString.concat("\nCurrent Appointments:\n" + getAppointments());
		
		return finalString;
	}
	
	/**
	 * Gets a formatted string that represents the simple information of this animal. This string consists
	 * of this animal's owner's name, the name of the animal itself, its age, its kind and its type.
	 * Each of these are separated by " // " in the string, for example: . . . [name] // [age] . . .
	 * @return A formatted string of this animal's essential information.
	 */
	public String toString() {
		String animalInfo;
		
		animalInfo = String.format("%-20s // %-13s // %3d // %-11s // %s", owner.getName(), name, age, this.getClass().getSimpleName(), type);
		
		return animalInfo;
	}
}

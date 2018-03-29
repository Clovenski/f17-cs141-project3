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

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is solely used to generate test data for the program. It essentially
 * creates randomly generated animals, clients and appointments and contains each of them
 * in an appropriate {@code ArrayList<T>} object, which are then ready to be referred to in
 * an invocation of their respective get methods.
 * <p>
 * Please see the documentation for the constructor, {@linkplain #Tester()}, for the complete process of generating the test data.
 * @author Joel Tengco
 */
public class Tester {
	/**
	 * A list containing test appointments.
	 */
	private ArrayList<Appointment> testAppointmentList;
	/**
	 * A list containing test animals.
	 */
	private ArrayList<Animal> testAnimalList;
	/**
	 * A list containing test clients.
	 */
	private ArrayList<Client> testClientList;
	/**
	 * The random number generator used to create the test data.
	 */
	private Random rng;
	
	/**
	 * Creates a new {@code Tester} object with test lists containing objects with
	 * randomly generated data.
	 * <p>
	 * First a pool of 20 clients, each named client1, client2, . . ., client20 with arbitrary information is created.
	 * <p>
	 * Then, 33 birds, 33 dogs, and 34 fishes for a total of 100 animals are created all with similar naming as how
	 * the clients are named, and each of these animals are assigned a random client from the aforementioned pool of clients.
	 * Also, each animal's type is randomly selected according to their 5 respective possible type values.
	 * <p>
	 * Finally, 100 appointments are created from randomly selecting an animal and adding an appointment to this animal
	 * with a random date and time.
	 */
	public Tester() {
		testAppointmentList = new ArrayList<Appointment>();
		testAnimalList = new ArrayList<Animal>();
		testClientList = new ArrayList<Client>();
		rng = new Random();
		
		
		Client newClient;
		ArrayList<Client> clientPool = new ArrayList<Client>();
		
		for(int i = 0; i < 20; i++) {
			newClient = new Client("client" + (i + 1), "This is a test address, which is just a string that is long enough", 9009009000L);
			clientPool.add(newClient);
		}
		
		Animal newAnimal;
		Client targetClient;
		
		Bird.BirdType[] birdTypePool = Bird.BirdType.values();
		for(int i = 0; i < 33; i++) {
			targetClient = clientPool.get(rng.nextInt(clientPool.size()));
			
			newAnimal = new Bird("bird" + (i + 1), rng.nextInt(30) + 1, targetClient, birdTypePool[rng.nextInt(birdTypePool.length)].name());
			testAnimalList.add(newAnimal);
			if(!testClientList.contains(targetClient))
				testClientList.add(targetClient);
		}
		
		Dog.DogType[] dogTypePool = Dog.DogType.values();
		for(int i = 0; i < 33; i++) {
			targetClient = clientPool.get(rng.nextInt(clientPool.size()));
			
			newAnimal = new Dog("dog" + (i + 1), rng.nextInt(30) + 1, targetClient, dogTypePool[rng.nextInt(dogTypePool.length)].name());
			testAnimalList.add(newAnimal);
			if(!testClientList.contains(targetClient))
				testClientList.add(targetClient);
		}
		
		Fish.FishType[] fishTypePool = Fish.FishType.values();
		for(int i = 0; i < 34; i++) {
			targetClient = clientPool.get(rng.nextInt(clientPool.size()));
			
			newAnimal = new Fish("fish" + (i + 1), rng.nextInt(30) + 1, targetClient, fishTypePool[rng.nextInt(fishTypePool.length)].name());
			testAnimalList.add(newAnimal);
			if(!testClientList.contains(targetClient))
				testClientList.add(targetClient);
		}
		
		
		Appointment newAppointment;
		Animal targetAnimal;
		Date newDate;
		Time newTime;
		for(int i = 0; i < 100; i++) {
			targetAnimal = testAnimalList.get(rng.nextInt(testAnimalList.size()));
			newDate = new Date(rng.nextInt(12) + 1, 1, 2018);
			newTime = new Time(rng.nextInt(24), rng.nextInt(60));
			newAppointment = new Appointment(targetAnimal.getOwner(), newDate, newTime);
			
			targetAnimal.addAppointment(newAppointment);
			testAppointmentList.add(newAppointment);
		}
	}
	
	/**
	 * Gets the appointment list containing test data.
	 * @return A list of test appointments.
	 */
	public ArrayList<Appointment> getTestAppointmentList() {
		return testAppointmentList;
	}
	
	/**
	 * Gets the animal list containing test data.
	 * @return A list of test animals.
	 */
	public ArrayList<Animal> getTestAnimalList() {
		return testAnimalList;
	}
	
	/**
	 * Gets the client list containing test data.
	 * @return A list of test clients.
	 */
	public ArrayList<Client> getTestClientList() {
		return testClientList;
	}
}

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class implements the program to be used by the veterinary office. The entirety of the interface to the user
 * is captured within this class.
 * <p>
 * It implements:
 * <ul>
 * <li>displaying a list of current appointments</li>
 * <li>setting some appointment to resolved</li>
 * <li>displaying a list of the animals in the registry</li>
 * <li>adding appointments to some animal</li>
 * <li>adding information to some animal</li>
 * <li>adding a new animal to the registry</li>
 * <li>adding a new client to the registry</li>
 * <li>saving the current data in the program to the registry</li>
 * <li>restoring the last saved data from the program's registry</li>
 * <li>displaying a list of all appointments in the registry, containing both outstanding and resolved appointments</li>
 * <li>search each of these lists with some specific value given by the user</li>
 * </ul>
 * @author Joel Tengco
 */
public class AdminProgram {
	/**
	 * A list that contains all of the objects of type {@code Appointment} in the registry,
	 * whether it be outstanding or resolved.
	 */
	private ArrayList<Appointment> masterAppointmentsList;
	/**
	 * A list that contains all of the objects of type {@code Animal} in the registry.
	 */
	private ArrayList<Animal> masterAnimalsList;
	/**
	 * A list that contains all of the objects of type {@code Client} in the registry;
	 */
	private ArrayList<Client> masterClientsList;
	/**
	 * A sublist of {@linkplain #masterAppointmentsList}, containing only the appointments
	 * that are not resolved, in other words, outstanding.
	 */
	private ArrayList<Appointment> currentAppointments;
	/**
	 * The {@code Scanner} object to capture the user's inputs.
	 */
	private Scanner keyboard;
	/**
	 * Determines if there were any changes made throughout the use of the program. This is mainly used
	 * to determine whether or not to make the save and restore options available to the user.
	 */
	private boolean changesMade;
	/**
	 * A list containing simple, informative strings of all the changes that were made throughout the use
	 * of the program, if any.
	 */
	private ArrayList<String> changes;
	
	/**
	 * Initializes a new instance of the program, ready to be started. If previously saved registry data
	 * exists, then it is properly loaded. The file that contains this program's registry data is
	 * named "program_registry".
	 */
	public AdminProgram() {
		File saveFile = new File("program_registry");
		
		if(saveFile.exists())
			restoreRegistry();
		else {
			masterAppointmentsList = new ArrayList<Appointment>();
			masterAnimalsList = new ArrayList<Animal>();
			masterClientsList = new ArrayList<Client>();
			currentAppointments = new ArrayList<Appointment>();
		}
	}
	
	/**
	 * This constructor, taking in an argument of type {@code Tester}, is used to test the functionality
	 * of the program with data generated from the {@code Tester} class.
	 * @param test The object that contains methods that are able to generate test data for this program.
	 */
	public AdminProgram(Tester test) {
		masterAppointmentsList = test.getTestAppointmentList();
		masterAnimalsList = test.getTestAnimalList();
		masterClientsList = test.getTestClientList();
		
		masterAppointmentsList.sort(new SortAppointmentsByDateAndTime());
		currentAppointments = new ArrayList<Appointment>();
		
		for(Appointment apptmnt : masterAppointmentsList)
			if(!apptmnt.isResolved())
				currentAppointments.add(apptmnt);
		
		masterAnimalsList.sort(new MedicalRecordByAnimalKind());
		
		System.out.println("////////////////////////////////////");
		System.out.println("///// PROGRAM TEST DATA LOADED /////");
		System.out.println("////////////////////////////////////");
		System.out.println();
		System.out.println("*Please see the Tester class to see how the test data was generated*");
		System.out.println();
	}
	
	/**
	 * The method that is used to start this program. Once this method is invoked, it will actually
	 * never return as the code in {@linkplain #quitProgram()} exits the system when the user opts
	 * to quit the program.
	 */
	public void start() {
		keyboard = new Scanner(System.in);
		changes = new ArrayList<String>();
		changesMade = false;
		startMenu();
	}
	
	/**
	 * This method represents the start menu for the user. Every other menu (list of options) for the user roots back
	 * to this one. It can be thought as the program's "main menu", and in the form of a tree consisting of all the different
	 * pathways the user can take throughout the program, it can be thought as the very top node.
	 * <p>
	 * {@linkplain #startMenu()}
	 * <ul>{@linkplain #checkAppointments()}</ul>
	 * <ul>{@linkplain #checkAnimals()}</ul>
	 * <ul>{@linkplain #addAnimal()}</ul>
	 * <ul>{@linkplain #showAllAppointments()}</ul>
	 * <ul>{@linkplain #saveRegistry()}</ul>
	 * <ul>{@linkplain #restoreRegistry()}</ul>
	 * <ul>{@linkplain #quitProgram()}</ul>
	 */
	private void startMenu() {
		ArrayList<String> startOptions = new ArrayList<String>();
		String input;
		boolean saveOptionsExist = false;
		
		startOptions.add("Check Current Appointments");
		startOptions.add("Check Animals");
		startOptions.add("Add New Animal");
		startOptions.add("Show All Appointments");
		startOptions.add("Quit");
		
		do {
			System.out.println("///// START MENU /////");
			System.out.println("Registry Info:");
			System.out.println();
			System.out.println("Current Appointments: " + currentAppointments.size());
			System.out.println("Total Appointments: " + masterAppointmentsList.size());
			System.out.println("Number of Animals: " + masterAnimalsList.size());
			System.out.println("Number of Clients: " + masterClientsList.size());
			System.out.println();
			System.out.println("Please enter the desired option:");
			displayOptions(startOptions);
			System.out.print("> ");
			input = getUserInput(startOptions);
			
			switch(input) {
			case "check current appointments":
				checkAppointments();
				break;
			case "check animals":
				checkAnimals();
				break;
			case "add new animal":
				addAnimal();
				break;
			case "show all appointments":
				showAllAppointments();
				break;
			case "save":
				saveRegistry();
				startOptions.remove("Save");
				startOptions.remove("Restore Registry");
				saveOptionsExist = false;
				changesMade = false;
				changes.clear();
				break;
			case "restore registry":
				restoreRegistry();
				startOptions.remove("Save");
				startOptions.remove("Restore Registry");
				saveOptionsExist = false;
				changesMade = false;
				changes.clear();
				break;
			case "quit":
				quitProgram();
				break;
			}
			
			if(changesMade && !saveOptionsExist) {
				startOptions.add(startOptions.indexOf("Quit"), "Save");
				startOptions.add(startOptions.indexOf("Quit"), "Restore Registry");
				saveOptionsExist = true;
			}
		} while(true);
	}
	
	/**
	 * This method saves the current status of all three of the master lists to the program's registry
	 * save file, named "program_registry".
	 */
	private void saveRegistry() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("program_registry"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(masterAppointmentsList);
			oos.writeObject(masterAnimalsList);
			oos.writeObject(masterClientsList);
			System.out.println("Successfully saved registry.\n");
			
			fos.close();
			oos.close();
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(Exception e) {
			System.err.println("ERROR: could not save registry");
		}
	}
	
	/**
	 * This method restores the data within the program's registry save file, named "program_registry",
	 * overwriting any data that the three master lists are currently pointing to,
	 * if they are in any way different than the save file.
	 */
	@SuppressWarnings("unchecked")
	private void restoreRegistry() {
		try {
			FileInputStream fis = new FileInputStream(new File("program_registry"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			masterAppointmentsList = (ArrayList<Appointment>)ois.readObject();
			masterAnimalsList = (ArrayList<Animal>)ois.readObject();
			masterClientsList = (ArrayList<Client>)ois.readObject();
			currentAppointments = new ArrayList<Appointment>();
			for(Appointment apptmnt : masterAppointmentsList)
				if(!apptmnt.isResolved())
					currentAppointments.add(apptmnt);
			
			fis.close();
			ois.close();
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch(Exception e) {
			System.err.print("ERROR: could not restore registry");
		}
		
	}
	
	/**
	 * This method properly quits the program and exits the system. If any unsaved changes
	 * were made throughout the use of the program the user is given the option to whether or
	 * not save those changes, as well as view the changes that are going to be saved before
	 * making the decision. The user is also able to cancel this quit action.
	 */
	private void quitProgram() {
		if(changesMade) {
			ArrayList<String> options = new ArrayList<String>();
			String input;
			
			options.add("Yes");
			options.add("No");
			options.add("View Changes");
			options.add("Cancel");
			
			do {
				System.out.println("Would you like to save the changes that have been made?");
				displayOptions(options);
				System.out.print("> ");
				input = getUserInput(options);
				
				switch(input) {
				case "yes":
					saveRegistry();
					System.exit(0);
				case "no":
					System.exit(0);
				case "view changes":
					displayList(changes);
					options.remove("View Changes");
					break;
				case "cancel":
					return;
				}
			} while(true);
		}
		
		System.exit(0);
	}
	
	/**
	 * This method displays a list of the current appointments, in other words these appointments
	 * are not resolved and are of outstanding status. This is where the user can change one of the
	 * listed appointments to resolved, search the list and view one of the clients involed in this
	 * list.
	 * <p>
	 * {@linkplain #checkAppointments()}
	 * <ul>{@linkplain #setAsResolved(ArrayList)}</ul>
	 * <ul>{@linkplain #searchAppointments(ArrayList)}</ul>
	 * <ul>{@linkplain #viewClientInfo(ArrayList)}</ul>
	 */
	private void checkAppointments() {
		ArrayList<String> options = new ArrayList<String>();
		ArrayList<Appointment> sublist;
		String input;
		int listInitialSize;
		
		sublist = new ArrayList<Appointment>(currentAppointments);
		
		if(sublist.size() != 0) {
			options.add("Change an Appointment to Resolved");
			options.add("Search Appointments");
			options.add("View Client Info");
		}
		options.add("Return");
		
		do {
			System.out.printf("###. %-20s // %-17s // %s\n", "CLIENT NAME", "DATE", "TIME");
			displayList(sublist);
			System.out.println("Please enter the desired option:");
			displayOptions(options);
			System.out.print("> ");
			input = getUserInput(options);
			
			switch(input) {
			case "change an appointment to resolved":
				setAsResolved(sublist);
				if(sublist.size() == 0) {
					System.out.println("This list is now empty, press [ENTER] to return");
					keyboard.nextLine();
					return;
				}
				break;
			case "search appointments":
				listInitialSize = sublist.size();
				searchAppointments(sublist);
				if(listInitialSize != sublist.size() && !options.contains("Reset Searched List"))
					options.add(options.indexOf("Search Appointments") + 1, "Reset Searched List");
				break;
			case "reset searched list":
				sublist = new ArrayList<Appointment>(currentAppointments);
				options.remove("Reset Searched List");
				break;
			case "view client info":
				viewClientInfo(sublist);
				break;
			case "return":
				return;
			}
		} while(true);
	}
	
	/**
	 * This method displays all the appointments, resolved or not, onto the screen. If there are no
	 * appointments to display, an appropriate message is shown instead.
	 */
	private void showAllAppointments() {
		String status;
		
		System.out.printf("%-20s // %-17s // %-8s // %s\n\n", "CLIENT NAME", "DATE", "TIME", "STATUS");
		if(masterAppointmentsList.size() == 0) {
			System.out.println("No records of any appointments are available\n");
			System.out.println("Press [ENTER] to return");
			keyboard.nextLine();
			return;
		}
		
		for(Appointment apptmnt : masterAppointmentsList) {
			status = apptmnt.isResolved() ? "RESOLVED" : "OUTSTANDING";
			System.out.println(apptmnt.toString() + " // " + status);
		}
		System.out.println();
		System.out.println("Press [ENTER] to return");
		keyboard.nextLine();
	}
	
	/**
	 * This method is called whenever the user wants to view more information about a client
	 * within the given list of appointments. The user is prompted to enter the number of the
	 * appointment in the list that contains the desired client. This client's name, phone number
	 * and address is then displayed.
	 * @param list The list of appointments for the user to choose from.
	 */
	private void viewClientInfo(ArrayList<Appointment> list) {
		int inputNumber;
		Client targetClient;
		String clientInfo;
		
		do {
			System.out.print("Enter the number of the appointment with the desired client: ");
			inputNumber = getUserNumberInput();
			try {
				targetClient = list.get(inputNumber - 1).getClient();
			} catch(IndexOutOfBoundsException e) {
				System.err.println("ERROR: please enter a valid option number");
				continue;
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
				continue;
			}
			clientInfo = targetClient.toString();
			System.out.printf("%-20s // %-12s // %s\n", "NAME", "PHONE NUMBER", "ADDRESS");
			System.out.println(clientInfo);
			break;
		} while(true);
		
		System.out.println();
		System.out.println("Press [ENTER] to return");
		keyboard.nextLine();
	}
	
	/**
	 * This method is called whenever the user wants to set an appointment to a status of resolved
	 * instead of outstanding. The user is prompted to choose which appointment from the list
	 * to change to resolved.
	 * @param list The list of appointments for the user to choose from.
	 */
	private void setAsResolved(ArrayList<Appointment> list) {
		int inputNumber;
		Appointment targetAppointment;
		String changeMessage;
		
		do {
			System.out.print("Enter the number of the appointment to change to resolved: ");
			inputNumber = getUserNumberInput();
			try {
				targetAppointment = list.get(inputNumber - 1);
				targetAppointment.setAsResolved();
				break;
			} catch(IndexOutOfBoundsException e) {
				System.err.println("ERROR: please enter a valid option number");
				continue;
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
				continue;
			}
		} while(true);
		
		changeMessage = "Set Appointment to Resolved: " + targetAppointment.toString();
		System.out.println(changeMessage);
		changes.add(changeMessage);
		changesMade = true;
		currentAppointments.remove(targetAppointment);
		list.remove(targetAppointment);
		
		System.out.println();
		System.out.println("Press [ENTER] to return");
		keyboard.nextLine();
	}
	
	/**
	 * This method is called whenever the user wants to search the given list
	 * of appointments. The user is able to search this list by client name
	 * or by date.
	 * <p>
	 * {@linkplain #searchAppointments(ArrayList)}
	 * <ul>{@linkplain #searchAppointmentsByClient(ArrayList)}</ul>
	 * <ul>{@linkplain #searchAppointmentsByDate(ArrayList)}</ul>
	 * @param list The list of appointments to search through.
	 */
	private void searchAppointments(ArrayList<Appointment> list) {
		String input;
		ArrayList<String> options = new ArrayList<String>();
		options.add("Search by Client");
		options.add("Search by Date");
		options.add("Return");
		
		System.out.println("Please enter the desired option:");
		displayOptions(options);
		System.out.print("> ");
		input = getUserInput(options);
		
		switch(input) {
		case "search by client":
			searchAppointmentsByClient(list);
			break;
		case "search by date":
			searchAppointmentsByDate(list);
			break;
		case "return":
			return;
		}
	}
	
	/**
	 * This method searches the given list according to a string given by the user.
	 * The string inputed by the user is then compared, ignoring case, to all the client
	 * names in the given list. The list is then modified to only contain the matches.
	 * @param list The list that is being searched through and modified.
	 */
	private void searchAppointmentsByClient(ArrayList<Appointment> list) {
		String targetName;
		Appointment target;
		
		System.out.print("Enter the name of the client(case-insensitive): ");
		targetName = keyboard.nextLine();
		target = new Appointment(new Client(targetName));
		search(target, list, new AppointmentByOwner());
	}
	
	/**
	 * This method searches the given list according to a date given by the user.
	 * The user is prompted and will keep being prompted until a valid date is specified.
	 * The given list is then modified to only contain the appointments with a date that
	 * matches the one specified by the user.
	 * @param list The list to be searched through and modified.
	 */
	private void searchAppointmentsByDate(ArrayList<Appointment> list) {
		int monthInput;
		int dayInput;
		int yearInput;
		Date targetDate;
		Appointment target;
		
		do {
			System.out.println("Date to be searched:");
			System.out.print("Month: ");
			monthInput = getUserNumberInput();
			System.out.print("Day: ");
			dayInput = getUserNumberInput();
			System.out.print("Year: ");
			yearInput = getUserNumberInput();
			
			try {
				targetDate = new Date(monthInput, dayInput, yearInput);
				
				target = new Appointment(targetDate);
				search(target, list, new AppointmentByDate());
				return;
			} catch(IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
			}
		} while(true);
	}
	
	/**
	 * This method displays a list of all the animals within this program's
	 * registry, if any. The user is then given the option to search through
	 * this list to form a sublist, or view a specific animal's records. The user
	 * is able to view a specific animal's records from this list and from any of
	 * its sublists.
	 * <p>
	 * {@linkplain #checkAnimals()}
	 * <ul>{@linkplain #viewAnimalRecord(ArrayList)}</ul>
	 * <ul>{@linkplain #searchAnimals(ArrayList)}</ul>
	 */
	private void checkAnimals() {
		ArrayList<String> options = new ArrayList<String>();
		ArrayList<Animal> sublist = new ArrayList<Animal>(masterAnimalsList);
		String input;
		int listInitialSize;
		
		if(sublist.size() != 0) {
			options.add("View Animal Record");
			options.add("Search Animals");
		}
		options.add("Return");
		
		do {
			System.out.printf("###. %-20s // %-13s // %-3s // %-11s // %s\n", "OWNER NAME", "ANIMAL NAME", "AGE", "ANIMAL KIND", "ANIMAL TYPE");
			displayList(sublist);
			System.out.println("Please enter the desired option:");
			displayOptions(options);
			System.out.print("> ");
			input = getUserInput(options);
			
			switch(input) {
			case "view animal record":
				viewAnimalRecord(sublist);
				break;
			case "search animals":
				listInitialSize = sublist.size();
				searchAnimals(sublist);
				if(listInitialSize != sublist.size() && !options.contains("Reset List"))
					options.add(options.indexOf("Search Animals") + 1, "Reset List");
				if(sublist.size() == 0) {
					options.remove("View Animal Record");
					options.remove("Search Animals");
				}
				break;
			case "reset list":
				sublist = new ArrayList<Animal>(masterAnimalsList);
				if(!options.contains("View Animal Record")) {
					options.add(0, "View Animal Record");
					options.add(1, "Search Animals");
				}
				options.remove("Reset List");
				break;
			case "return":
				return;
			}
		} while(true);
	}
	
	/**
	 * This method displays the full information on an animal within the given list, specified by the user's input.
	 * Once an animal is specified, the user is then shown its full information and given the option to add info to its
	 * records. This is all done with {@link #changeAnimalRecord(Animal)}.
	 * @param list The list of animals for the user to choose from.
	 */
	private void viewAnimalRecord(ArrayList<Animal> list) {
		int inputNumber;
		Animal targetAnimal;
		
		do {
			System.out.print("Enter the number of the animal to view: ");
			inputNumber = getUserNumberInput();
			
			try {
				targetAnimal = list.get(inputNumber - 1);
			} catch(IndexOutOfBoundsException e) {
				System.err.println("ERROR: please enter a valid option number");
				continue;
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
				continue;
			}
			
			changeAnimalRecord(targetAnimal);
			break;
		} while(true);
	}
	
	/**
	 * This method encapsulates the options for the user to add appointments, past and current diseases, and vaccinations
	 * to the given animal.
	 * <p>
	 * {@linkplain #changeAnimalRecord(Animal)}
	 * <ul>{@linkplain #addAppointment(Animal)}</ul>
	 * @param targetAnimal The animal in which the user can add information to.
	 */
	private void changeAnimalRecord(Animal targetAnimal) {
		String inputOption;
		String changeMessage = "";
		ArrayList<String> options = new ArrayList<String>();
		options.add("Add Appointment");
		options.add("Add Past Disease");
		options.add("Add Current Disease");
		options.add("Add Vaccination");
		options.add("Return");
		
		do {
			System.out.printf("%-20s // %-13s // %-3s // %-11s // %s\n", "OWNER NAME", "ANIMAL NAME", "AGE", "ANIMAL KIND", "ANIMAL TYPE");
			System.out.println(targetAnimal.getFullInfo());
			System.out.println("Please enter the desired option:");
			displayOptions(options);
			System.out.print("> ");
			
			inputOption = getUserInput(options);
			switch(inputOption) {
			case "add appointment":
				addAppointment(targetAnimal);
				continue;
			case "add past disease":
				System.out.print("Please enter the Disease name: ");
				targetAnimal.addPastDisease(keyboard.nextLine());
				changeMessage = "Added a past disease to " + targetAnimal.getName() + ", owned by " + targetAnimal.getOwnerName();
				break;
			case "add current disease":
				System.out.print("Please enter the Disease name: ");
				targetAnimal.addCurrentDisease(keyboard.nextLine());
				changeMessage = "Added a current disease to " + targetAnimal.getName() + ", owned by " + targetAnimal.getOwnerName();
				break;
			case "add vaccination":
				System.out.print("Please enter the Vaccination name: ");
				targetAnimal.addVaccination(keyboard.nextLine());
				changeMessage = "Added a vaccination to " + targetAnimal.getName() + ", owned by " + targetAnimal.getOwnerName();
				break;
			case "return":
				return;
			}
			
			System.out.println(changeMessage);
			changes.add(changeMessage);
			changesMade = true;
			
			System.out.println();
			System.out.println("Press [ENTER] to return");
			keyboard.nextLine();
		} while(true);
	}
	
	/**
	 * This method adds an appointment to the given animal. The user is prompted and will keep
	 * being prompted until a valid date and time is inputed. The newly created appointment is
	 * then added to the target animal.
	 * @param targetAnimal The animal in which the newly created appointment will be added to.
	 */
	private void addAppointment(Animal targetAnimal) {
		int monthInput;
		int dayInput;
		int yearInput;
		Date dateInput;
		int hourInput;
		int minuteInput;
		Time timeInput;
		Appointment newApptmnt;
		String changeMessage;
		
		dateInput = new Date();
		do {
			try {
				System.out.println("Please enter the date for the new appointment:");
				System.out.print("Year: ");
				yearInput = getUserNumberInput();
				dateInput.setYear(yearInput);
				System.out.print("Month: ");
				monthInput = getUserNumberInput();
				dateInput.setMonth(monthInput);
				System.out.print("Day: ");
				dayInput = getUserNumberInput();
				dateInput.setDay(dayInput);
				break;
			} catch(IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				System.out.println();
				continue;
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
				System.out.println();
				continue;
			}
		} while(true);
		
		timeInput = new Time();
		do {
			try {
				System.out.println();
				System.out.println("Please enter the time for the new appointment:");
				System.out.print("Hour (0 - 23): ");
				hourInput = getUserNumberInput();
				timeInput.setHour(hourInput);
				System.out.print("Minute (0 - 59): ");
				minuteInput = getUserNumberInput();
				timeInput.setMinute(minuteInput);
			} catch(IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				continue;
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
				continue;
			}
			
			newApptmnt = new Appointment(targetAnimal.getOwner(), dateInput, timeInput);
			break;
		} while(true);
		
		
		targetAnimal.addAppointment(newApptmnt);
		masterAppointmentsList.add(newApptmnt);
		masterAppointmentsList.sort(new SortAppointmentsByDateAndTime());
		currentAppointments.add(newApptmnt);
		currentAppointments.sort(new SortAppointmentsByDateAndTime());
		
		changeMessage = "Added new appointment: " + newApptmnt.toString();
		System.out.println(changeMessage);
		changes.add(changeMessage);
		changesMade = true;
		
		System.out.println();
		System.out.println("Press [ENTER] to return");
		keyboard.nextLine();
	}

	/**
	 * This method searches the given list. The user is prompted to either search this list of animals by their owners,
	 * their kind, or their name.
	 * <p>
	 * {@linkplain #searchAnimals(ArrayList)}
	 * <ul>{@linkplain #searchAnimalsByOwner(ArrayList)}</ul>
	 * <ul>{@linkplain #searchAnimalsByKind(ArrayList)}</ul>
	 * <ul>{@linkplain #searchAnimalsByName(ArrayList)}</ul>
	 * @param list The list of animals to search through.
	 */
	private void searchAnimals(ArrayList<Animal> list) {
		String input;
		ArrayList<String> options = new ArrayList<String>();
		options.add("Search by Animal Owner");
		options.add("Search by Animal Kind");
		options.add("Search by Animal Name");
		options.add("Return");
		
		System.out.println("Please enter the desired option:");
		displayOptions(options);
		System.out.print("> ");
		input = getUserInput(options);
		
		switch(input) {
		case "search by animal owner":
			searchAnimalsByOwner(list);
			break;
		case "search by animal kind":
			searchAnimalsByKind(list);
			break;
		case "search by animal name":
			searchAnimalsByName(list);
			break;
		case "return":
			return;
		}
	}
	
	/**
	 * This method searches the given list of animals by their owner's name. The string inputed
	 * by the user is then compared, ignoring case, to every animal's owner's name in the given list.
	 * This list is modified to only contain the animals that match the target client's name.
	 * @param list The list of animals to be searched through and modified.
	 */
	private void searchAnimalsByOwner(ArrayList<Animal> list) {
		String targetName;
		Animal targetAnimal;
		
		System.out.print("Enter the name of the owner(case-insensitive): ");
		targetName = keyboard.nextLine();
		targetAnimal = new Animal(new Client(targetName));
		search(targetAnimal, list, new MedicalRecordByAnimalOwner());
	}
	
	/**
	 * This method searches the given list of animals by their kind. The user is properly
	 * prompted for which kind of animal to search this list by. Their options are to search
	 * by bird, dog or by fish. The list is then modified to only contain the animals of that
	 * kind. 
	 * @param list The list of animals to be searched through and modified.
	 */
	private void searchAnimalsByKind(ArrayList<Animal> list) {
		Animal targetAnimal = null;
		String input;
		ArrayList<String> options = new ArrayList<String>();
		options.add("Bird");
		options.add("Dog");
		options.add("Fish");
		options.add("Return");
		
		System.out.println("Search animal by:");
		displayOptions(options);
		System.out.print("> ");
		input = getUserInput(options);
		
		switch(input) {
		case "bird":
			targetAnimal = new Bird();
			break;
		case "dog":
			targetAnimal = new Dog();
			break;
		case "fish":
			targetAnimal = new Fish();
			break;
		case "return":
			return;
		}
		
		search(targetAnimal, list, new MedicalRecordByAnimalKind());
	}
	
	/**
	 * This method searches the given list of animals by their name. The string inputed
	 * by the user is then compared, ignoring case, to all of the animal's names in this list.
	 * The list is then modified to only contain the animals that satisfy this comparison, matching
	 * the user's input.
	 * @param list The list of animals to be searched through and modified.
	 */
	private void searchAnimalsByName(ArrayList<Animal> list) {
		String targetName;
		Animal targetAnimal;
		
		System.out.print("Enter the name of the animal(case-insensitive): ");
		targetName = keyboard.nextLine();
		targetAnimal = new Animal(targetName);
		search(targetAnimal, list, new MedicalRecordByAnimalName());
	}
	
	/**
	 * This method adds an animal to the program. This is the entry point to where all
	 * the data is created in the program, as a new appointment cannot be created until
	 * at least one animal is created, as well as the creation of a new client is done
	 * solely through this method.
	 * <p>
	 * The user is first prompted to choose which client is the owner of the new animal, or
	 * add a new client to the program, and then the user is prompted to properly define
	 * this animal's information.
	 */
	private void addAnimal() {
		int inputNumber;
		Client targetClient;
		String nameInput;
		int ageInput;
		String animalKindInput;
		String typeInput;
		Animal newAnimal = null;
		String changeMessage;
		ArrayList<String> options = new ArrayList<String>();
		
		System.out.println("Adding new animal:");
		for(Client client : masterClientsList)
			options.add(client.toString());
		options.add("[ADD NEW CLIENT]");
		
		System.out.printf("###. %-20s // %-12s // %s\n", "NAME", "PHONE NUMBER", "ADDRESS");
		displayList(options);
		System.out.print("Who is the owner of this animal? Enter the option number: ");
		
		do {
			inputNumber = getUserNumberInput();
			if(inputNumber == options.size()) {			// user has selected the last option,
				targetClient = getNewClient();			// which is to [ADD NEW CLIENT]
				break;
			}
			try {
				targetClient = masterClientsList.get(inputNumber - 1);
				break;
			} catch(IndexOutOfBoundsException e) {
				System.err.println("ERROR: please enter a valid option number");
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
			}
		} while(true);
		System.out.println("Owner selected: " + targetClient.getName());
		
		System.out.print("Animal Name: ");
		nameInput = keyboard.nextLine();
		do {
			try {
				System.out.print("Animal Age (in years): ");
				ageInput = getUserNumberInput();
				if(ageInput <= 0)
					throw new IllegalArgumentException("ERROR: please enter a positive number for the age");
				break;
			} catch(IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
			}
		} while(true);
		System.out.println("Animal kind:");
		options.clear();
		options.add("Bird");
		options.add("Dog");
		options.add("Fish");
		
		System.out.println("Please enter the kind of animal it is:");
		displayOptions(options);
		System.out.print("> ");
		animalKindInput = getUserInput(options);
		
		options.clear();
		switch(animalKindInput) {
		case "bird":
			for(Bird.BirdType type : Bird.BirdType.values())
				options.add(type.toString().toLowerCase());
			typeInput = getNewAnimalType("bird", options);
			newAnimal = new Bird(nameInput, ageInput, targetClient, typeInput);
			break;
		case "dog":
			for(Dog.DogType type : Dog.DogType.values())
				options.add(type.toString().toLowerCase());
			typeInput = getNewAnimalType("dog", options);
			newAnimal = new Dog(nameInput, ageInput, targetClient, typeInput);
			break;
		case "fish":
			for(Fish.FishType type : Fish.FishType.values())
				options.add(type.toString().toLowerCase());
			typeInput = getNewAnimalType("fish", options);
			newAnimal = new Fish(nameInput, ageInput, targetClient, typeInput);
			break;
		}
		
		masterAnimalsList.add(newAnimal);
		masterAnimalsList.sort(new MedicalRecordByAnimalKind());
		
		changeMessage = "Added a new animal: " + newAnimal.getName() + ", owned by " + newAnimal.getOwnerName();
		System.out.println(changeMessage);
		changes.add(changeMessage);
		changesMade = true;
		
		System.out.println();
		System.out.println("Press [ENTER] to return");
		keyboard.nextLine();
	}
	
	/**
	 * This method acquires the desired animal type by the user and returns a string representing
	 * that value.
	 * @param animalKindInput The kind of animal that the user has defined for the new animal to be added.
	 * This should be either bird, dog or fish.
	 * @param typeOptions A list of strings that contain the five different types the new animal can be defined as.
	 * The five options depend on the kind of animal.
	 * @return A string representing the desired type of animal.
	 */
	private String getNewAnimalType(String animalKindInput, ArrayList<String> typeOptions) {
		String animalTypeInput;
		
		System.out.println("Please enter the type of " + animalKindInput + " it is:");
		displayOptions(typeOptions);
		System.out.print("> ");
		animalTypeInput = getUserInput(typeOptions);
		
		return animalTypeInput;
	}
	
	/**
	 * This method both creates a new client in the program and returns this newly created client.
	 * The user is prompted and will keep being prompted until valid information is given about the
	 * new client.
	 * @return A direct reference to the newly created {@code Client} object.
	 */
	private Client getNewClient() {
		StringTokenizer inputLine;
		String nameInput;
		String addressInput;
		long phoneNumberInput;
		Client newClient;
		
		System.out.println("Creating new client:");
		System.out.print("Name: ");
		nameInput = keyboard.nextLine();
		System.out.print("Address: ");
		addressInput = keyboard.nextLine();
		System.out.print("Phone Number(##########, no spaces in between the 10 numbers): ");
		do {
			inputLine = new StringTokenizer(keyboard.nextLine());
			try {
				phoneNumberInput = Long.parseLong(inputLine.nextToken());
				newClient = new Client(nameInput, addressInput, phoneNumberInput);
				break;
			} catch(NoSuchElementException nsee) {
				System.err.println("ERROR: please enter a sequence of numbers before pressing [ENTER]");
				System.out.print("> ");
			} catch(NumberFormatException nfe) {
				System.err.println("ERROR: please enter numbers");
				System.out.print("> ");
			} catch(IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				System.out.print("> ");
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
				System.out.print("> ");
			}
		} while(true);
		
		masterClientsList.add(newClient);
		return newClient;
	}
	
	/**
	 * This is the method that is used by all five of the search methods. It iterates through the given
	 * list and compares each object in that list to the given target according to the given comparator.
	 * The list is then modified to contain only the objects that match the target according to the
	 * comparator.
	 * @param <T> The type of the objects contained inside the list to search through.
	 * @param target The object that contains the target information.
	 * @param list The list in which all its elements will be compared to the target.
	 * @param comparator The {@code Comparator} object that governs how the target and each element in the
	 * list will be compared.
	 */
	private <T> void search(T target, ArrayList<T> list, Comparator<T> comparator) {
		for(int i = list.size() - 1; i >= 0; i--)
			if(comparator.compare(target, list.get(i)) != 0)
				list.remove(i);
	}
	
	/**
	 * This method displays the given list in a numbered fashion, starting at 1 for the first
	 * element in the list. If the given list is of size 0, then it will print "Empty" instead.
	 * @param <T> The type of the objects contained inside the list to display.
	 * @param list The list containing the elements to be displayed onto the screen in a numbered
	 * fashion.
	 */
	private <T> void displayList(ArrayList<T> list) {
		int listNumber = 1;
		System.out.println();
		
		if(list.size() != 0)
			for(T element : list)
				System.out.printf("%03d. %s\n", listNumber++, element.toString());
		else
			System.out.println("Empty");
		
		System.out.println();
	}
	
	/**
	 * This method is used to display options in a numbered fashion. If the given list of options
	 * is empty, then this method does nothing but prints an empty line.
	 * @param options A list of strings that represent the desired options to be printed.
	 */
	private void displayOptions(ArrayList<String> options) {
		int optionNum = 1;
		
		for(String opt : options)
			System.out.println(optionNum++ + ". " + opt);
		System.out.println();
	}
	
	/**
	 * This method gets an input from the user and this input must be a number. An appropriate message
	 * is printed whenever an invalid input is given from the user.
	 * @return The number given by the user, and this number can be any number.
	 */
	private int getUserNumberInput() {
		StringTokenizer inputLine;
		int finalInput;
		
		do {
			try {
				inputLine = new StringTokenizer(keyboard.nextLine());
				
				if(inputLine.countTokens() == 0)
					throw new IllegalArgumentException("Please enter an input before pressing [ENTER]");
				
				finalInput = Integer.parseInt(inputLine.nextToken());
				return finalInput;
			} catch(NumberFormatException nfe) {
				System.err.println("ERROR: please enter a number");
				System.out.print("> ");
			} catch(IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				System.out.print("> ");
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
				System.out.print("> ");
			}
		} while(true);
	}
	
	/**
	 * This method gets an input from the user, and only returns when one of the
	 * options in the given list of strings is chosen. The user chooses an option
	 * by inputing a number that represents the desired option's index in the list,
	 * plus one. So, if the player desires the first option in the list, the input
	 * would be 1. The returned string is the desired option/string converted to
	 * lower case.
	 * @param options A list of string representing the options given to the user.
	 * @return One of the strings in the given list, specified by the user, converted to lower case.
	 */
	private String getUserInput(ArrayList<String> options) {
		StringTokenizer inputLine;
		int finalInput;
		
		do {
			try {
				inputLine = new StringTokenizer(keyboard.nextLine());
				
				if(inputLine.countTokens() == 0)
					throw new IllegalArgumentException("Please enter an input before pressing [ENTER]");
				
				finalInput = Integer.parseInt(inputLine.nextToken());
				if(finalInput > 0 && finalInput <= options.size())
					return options.get(finalInput - 1).toLowerCase();
				else
					throw new IllegalArgumentException("ERROR: please enter a valid option number");
			} catch(NumberFormatException nfe) {
				System.err.println("ERROR: please enter a number corresponding to the desired option");
				System.out.print("> ");
			} catch(IllegalArgumentException iae) {
				System.err.println(iae.getMessage());
				System.out.print("> ");
			} catch(Exception e) {
				System.err.println("ERROR: please try again");
				System.out.print("> ");
			}
		} while(true);
	}
}

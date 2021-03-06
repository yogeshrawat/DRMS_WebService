/*
 * 
 */
package Client;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import Utility.ValidateInput;
import Interface.LibraryInterface;

/**
 * The Class StudentClient.
 */
public class StudentClient extends Client {

	/** The Concordia server. */
	static LibraryInterface ConcordiaServer;

	/** The Ottawa server. */
	static LibraryInterface OttawaServer;

	/** The Waterloo server. */
	static LibraryInterface WaterlooServer;

	/** The Constant Waterloo. */
	static final String Concordia = "Concordia", Ottawa = "Ottawa",
			Waterloo = "Waterloo";

	/** The Constant portWaterloo. */
	static final String portConcordia = "50001", portOttawa = "50002",
			portWaterloo = "50003";

	/** The institute name. */
	protected static String instituteName;

	/**
	 * Server validation.
	 *
	 * @param portNumber
	 *            the port number
	 * @param strInstituteName
	 *            the str institute name
	 * @return the library interface
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public LibraryInterface ServerValidation(String portNumber,
			String strInstituteName) throws MalformedURLException {
		URL url = new URL("http://localhost:" + portNumber + "/"
				+ strInstituteName + "/ws?wsdl");
		QName qname = new QName("http://server/", "LibraryServerService");
		Service service = Service.create(url, qname);
		LibraryInterface library = service.getPort(LibraryInterface.class);
		return library;
	}

	// Menu to display actions that are need to perform by student
	/**
	 * Show menu.
	 */
	public static void showMenu() {
		System.out.println("DRMS Student Client System \n");
		System.out.println("Please select an option");
		System.out.println("1. Create An Account.");
		System.out.println("2. Reserve a Book");
		System.out.println("3. Reserve a Book InterLibraray");
		System.out.println("4. Return book");
		System.out.println("5. Exit");
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		try {
			StudentClient objClient = new StudentClient();
			LibraryInterface objServer = null;
			Scanner keyboard = new Scanner(System.in);
			Integer userInput = 0;
			String userName = null, password = null, institution = null, portNumber = null;
			boolean success = false;

			while (true) {
				showMenu();

				userInput = Integer.parseInt(objClient
						.InputStringValidation(keyboard));
				switch (userInput) {
				case 1:
					System.out.println("First Name: ");
					String firstName = objClient
							.InputStringValidation(keyboard);
					System.out.println("Last Name: ");
					String lastName = objClient.InputStringValidation(keyboard);
					System.out.println("Email: ");
					String emailAddress = objClient
							.InputStringValidation(keyboard);

					System.out.println("Phone No: ");
					ValidateInput v = new ValidateInput();
					String phoneNumber = v.validatePhNo(keyboard.nextLine()
							.toString());
					System.out.println("User Name: ");
					userName = v.validateUserName(keyboard.nextLine()
							.toString());
					System.out.println("Password: ");
					password = v.validate(keyboard.nextLine().toString());
					institution = getEducationalInstituteFromUser();
					portNumber = getPortNumber(institution);
					objServer = objClient.ServerValidation(portNumber,
							institution);
					success = objServer.createAccount(firstName, lastName,
							emailAddress, phoneNumber, userName, password,
							institution);
					if (success) {
						System.out.println("Success");
						File fi = new File(".\\logs\\students\\" + userName
								+ ".txt");
						FileWriter fw = new FileWriter(fi);
						objClient.setLogger(userName, ".\\logs\\students\\"
								+ userName + ".txt");
						objClient.logger
								.info("Account created successfully for user "
										+ userName);
						fw.close();
					} else {
						File fi = new File(".\\logs\\students\\" + userName
								+ ".txt");
						FileWriter fw = new FileWriter(fi);
						objClient.setLogger(userName, ".\\logs\\students\\"
								+ userName + ".txt");
						objClient.logger
								.info("Account already exist with username as : "
										+ userName);
						fw.close();
					}
					break;
				case 2:
					boolean flag = true;
					String bookName = "";
					String authorName = "";
					ValidateInput v2 = new ValidateInput();
					while (flag) {
						System.out.println("User Name: ");
						userName = v2.validateUserName(keyboard.nextLine()
								.toString());
						System.out.println("Password: ");
						password = v2.validate(keyboard.nextLine().toString());
						System.out.println("Book Name: ");
						bookName = objClient.InputStringValidation(keyboard);
						System.out.println("Author: ");
						authorName = objClient.InputStringValidation(keyboard);
						institution = getEducationalInstituteFromUser();
						portNumber = getPortNumber(institution);
						objServer = objClient.ServerValidation(portNumber,
								institution);
						int loginResult = objServer.checkUser(userName,
								password, institution);
						switch (loginResult) {
						case 1:
							System.out.println("Redirecting to " + institution
									+ " server");
							flag = false;
							break;

						case 0:
							System.out.println("You are not registered with "
									+ institution);
							System.out
									.println("Press 1 to continue 0 to Exit.");
							int in1 = objClient.InputIntValidation(keyboard);
							if (in1 == 1) {
								break;
							} else
								System.exit(0);

						case 2:
							System.out
									.println("Invalid Password. Please try again");
							System.out
									.println("Press 1 to continue 0 to Exit.");
							int in2 = objClient.InputIntValidation(keyboard);
							if (in2 == 1) {
								keyboard = null;
								break;
							}

							else
								System.exit(0);
						}
					}
					success = objServer.reserveBook(userName, password,
							bookName, authorName);
					if (success) {
						System.out.println("Book Reserved successfully ");
						objClient.setLogger(userName, ".\\logs\\students\\"
								+ userName + ".txt");
						objClient.logger
								.info("Book reserved successfully for user "
										+ userName);
					} else {
						System.out
								.println("Book could not be reserved  "
										+ " : This may be because either the book is not available or you have  already reserved the "
										+ bookName);

						objClient.setLogger(userName, ".\\logs\\students\\"
								+ userName + ".txt");
						objClient.logger
								.info("Book could not be reserved for : "
										+ userName);

					}
					break;

				case 3:
					System.out.println("User Name: ");
					ValidateInput v3 = new ValidateInput();
					userName = v3.validateUserName(keyboard.nextLine()
							.toString());
					System.out.println("Password: ");
					password = v3.validate(keyboard.nextLine().toString());
					System.out.println("Book Name: ");
					bookName = objClient.InputStringValidation(keyboard);
					System.out.println("Author: ");
					authorName = objClient.InputStringValidation(keyboard);
					institution = getEducationalInstituteFromUser();
					portNumber = getPortNumber(institution);
					objServer = objClient.ServerValidation(portNumber,
							institution);
					if (objServer.reserveInterLibrary(userName, password,
							bookName, authorName)) {
						objClient.setLogger(userName, ".\\logs\\students\\"
								+ userName + ".txt");
						objClient.logger
								.info("Interlibrary : Book reserved successfully for user "
										+ userName);
						System.out
								.println(" Interlibrary : Book reserved successfully");

					} else {
						objClient.setLogger(userName, ".\\logs\\students\\"
								+ userName + ".txt");
						objClient.logger
								.info("Book could not be reserved for : "
										+ userName);
						System.out
								.println(" Interlibrary : Book could not be reserved ");

					}
					break;

				case 4:
					System.out.println("User Name: ");
					ValidateInput v4 = new ValidateInput();
					userName = v4.validateUserName(keyboard.nextLine()
							.toString());
					System.out.println("Password: ");
					password = v4.validate(keyboard.nextLine().toString());
					System.out.println("Book Name: ");
					bookName = objClient.InputStringValidation(keyboard);
					System.out.println("Author: ");
					authorName = objClient.InputStringValidation(keyboard);
					institution = getEducationalInstituteFromUser();
					portNumber = getPortNumber(institution);
					objServer = objClient.ServerValidation(portNumber,
							institution);
					if (objServer.returnBook(userName, password, bookName,
							authorName)) {
						objClient.setLogger(userName, ".\\logs\\students\\"
								+ userName + ".txt");
						objClient.logger
								.info(" Book retrurned successfully by user "
										+ userName);
						System.out.println(" Book returned successfully");

					} else {
						objClient.setLogger(userName, ".\\logs\\students\\"
								+ userName + ".txt");
						objClient.logger
								.info("Book could not be returned for : "
										+ userName);
						System.out.println(" Book could not be returned");

					}

					break;

				case 5:
					System.out.println("Thank You \n Have a nice day!");
					keyboard.close();
					System.exit(0);
				default:
					System.out.println("Invalid input");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the port number.
	 *
	 * @param institution
	 *            the institution
	 * @return the port number
	 */
	private static String getPortNumber(String institution) {

		switch (institution) {
		case Concordia:
			return portConcordia;
		case Ottawa:
			return portOttawa;
		case Waterloo:
			return portWaterloo;
		default:
			return null;
		}
	}

	/**
	 * Gets the educational institute from user.
	 *
	 * @return the educational institute from user
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static String getEducationalInstituteFromUser() throws IOException {
		Integer ans = 0;
		System.out.println("Institution Name: ");
		while (true) {
			System.out.println("Please select a valid option:");
			System.out.println("1 for Concordia");
			System.out.println("2 for Ottawa");
			System.out.println("3 for Waterloo");
			Client objClient = new StudentClient();
			Scanner keyboard = new Scanner(System.in);
			ans = Integer.parseInt(objClient.InputStringValidation(keyboard));
			if (ans == 1 || ans == 2 || ans == 3) {
				break;
			} else {
				System.out.println("Invalid input!");
			}
		}
		switch (ans) {
		case 1:
			return Concordia;
		case 2:
			return Ottawa;
		case 3:
			return Waterloo;
		}
		return null;
	}
}

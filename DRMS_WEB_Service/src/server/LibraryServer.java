/*
 * 
 */
package server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Interface.LibraryInterface;
import LibraryObjects.*;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class LibraryServer.
 */
@WebService(endpointInterface = "Interface.LibraryInterface")
@SOAPBinding(style = Style.RPC)
public class LibraryServer extends Thread implements LibraryInterface {

	/** The table students. */
	private HashMap<Character, ArrayList<Student>> tableStudents = new HashMap<Character, ArrayList<Student>>();

	/** The table books. */
	private HashMap<String, Book> tableBooks = new HashMap<String, Book>();

	private String instituteName;
	static final String Concordia = "Concordia", Ottawa = "Ottawa",
			Waterloo = "Waterloo";
	static final String portConcordia = "50001", portOttawa = "50002",
			portWaterloo = "50003";
	private static String[] ServerNames = new String[] { Concordia, Ottawa,
			Waterloo };

	private static int Default_Reserve_Period = 14;

	/** The logger. */
	private Logger logger;

	/**
	 * Instantiates a new library server.
	 *
	 * @param instituteName
	 *            the institute name
	 */
	public LibraryServer(String instituteName) {
		this.instituteName = instituteName;
		this.setLogger(".\\logs\\library\\" + instituteName + ".txt");
	}

	/**
	 * Instantiates a new library server.
	 */
	public LibraryServer() {
	}

	/**
	 * Gets the books table.
	 *
	 * @return the books table
	 */
	public HashMap<String, Book> getBooksTable() {
		return tableBooks;
	}

	/**
	 * Sets the logger.
	 *
	 * @param instituteName
	 *            the new logger
	 */
	private void setLogger(String instituteName) {
		try {
			this.logger = Logger.getLogger(this.instituteName);
			FileHandler fileTxt = new FileHandler(instituteName);
			SimpleFormatter formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			logger.addHandler(fileTxt);
		} catch (Exception err) {
			System.out
					.println("Couldn't Initiate Logger. Please check file permission");
		}
	}

	/**
	 * Adds the data.
	 */
	public void addData() {
		if (this.instituteName == "Concordia") {
			for (int j = 1; j < 3; j++) {

				Book book = new Book("Book" + j, "Author" + j, 10);
				this.tableBooks.put(book.getName(), book);
			}
			Book book = new Book("Book3", "Author3", 10);
			this.tableBooks.put("Book3", book);

		} else {
			for (int j = 2; j < 3; j++) {
				Book book = new Book("Book" + j, "Author" + j, 0);
				this.tableBooks.put(book.getName(), book);
			}
			Book book = new Book("Book1", "Author1", 10);
			this.tableBooks.put(book.getName(), book);
		}

		ArrayList<Student> s = new ArrayList<Student>();
		for (char i = 'A'; i <= 'Z'; i++) {
			this.tableStudents.putIfAbsent(i, s);
		}

		if (this.instituteName == "Concordia") {
			this.createAccount("yogesh", "rawat", "yogesh@gmail.com",
					"5145156743", "yogesh", "yogesh", this.instituteName);
			this.reserveBook("yogesh", "yogesh", "Book1", "Author1");
			ArrayList<Student> list = this.tableStudents.get('y');

			Book Book1 = new Book("Book2", "Author2", 8);
			for (Student student : list) {
				if (student.getUserName().equals("yogesh")) {
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

		if (this.instituteName == "Ottawa") {
			this.createAccount("aron", "engineer", "aron@gmail.com",
					"5145156743", "aron123", "aron123", this.instituteName);
			ArrayList<Student> list = this.tableStudents.get('a');

			Book Book1 = new Book("Book1", "Author1", 8);
			for (Student student : list) {
				if (student.getUserName().equals("aron")) {
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

		if (this.instituteName == "Waterloo") {
			this.createAccount("ashish", "guhe", "ashish@gmail.com",
					"5145656743", "ashish", "ashish", this.instituteName);
			ArrayList<Student> list = this.tableStudents.get('a');

			Book Book1 = new Book("Book1", "Author1", 8);
			for (Student student : list) {
				if (student.getUserName().equals("ashish")) {
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Interface.LibraryInterface#createAccount(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean createAccount(String strFirstName, String strLastName,
			String strEmailAddress, String strPhoneNumber, String strUsername,
			String strPassword, String strEducationalInstitution) {
		if (isExist(strUsername, strEducationalInstitution)) {
			return false;
		} else {
			Student objStudent = new Student(strUsername, strPassword,
					strEducationalInstitution);
			objStudent.setFirstName(strFirstName);
			objStudent.setLastName(strLastName);
			objStudent.setEmailAddress(strEmailAddress);
			objStudent.setPhoneNumber(strPhoneNumber);

			// Add student to HashTable 'tableStudents' with Lock
			if (tableStudents.get(strUsername.charAt(0)) != null) {
				synchronized (tableStudents.get(strUsername.charAt(0))) {
					ArrayList<Student> objNewStudent = tableStudents
							.get(strUsername.charAt(0));
					if (objNewStudent == null) {
						objNewStudent = new ArrayList<Student>();
						tableStudents.put(strUsername.charAt(0), objNewStudent);
					}
					objNewStudent.add(objStudent);

					logger.info("New User added to the library with username as : "
							+ objStudent.getUserName());
				}
			} else {
				ArrayList<Student> objNewStudent = tableStudents
						.get(strUsername.charAt(0));
				if (objNewStudent == null) {
					objNewStudent = new ArrayList<Student>();
					tableStudents.put(strUsername.charAt(0), objNewStudent);
				}
				objNewStudent.add(objStudent);

				logger.info("New User added to the library with username as : "
						+ objStudent.getUserName());

			}

			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Interface.LibraryInterface#reserveBook(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean reserveBook(String strUsername, String strPassword,
			String strBookName, String strAuthor) {
		boolean success = false;
		int iLoginResult = 0;
		Student objStudent = null;
		objStudent = getStudent(strUsername);
		HashMap<String, Book> tableBooks = this.getBooksTable();
		HashMap<Book, Integer> reservedBooks = objStudent.getReservedBooks();
		Book book = tableBooks.get(strBookName);
		if (reservedBooks.containsKey(book)) {
			logger.info(strBookName + ": is already reserved for the user "
					+ strUsername);
			System.out.println(strBookName
					+ ": is already reserved for the user " + strUsername);
			return success;
		}
		Book objBook = tableBooks.get(strBookName);
		if (objStudent != null) {
			iLoginResult = checkUser(strUsername, strPassword,
					objStudent.getInst());
			if (iLoginResult == 1) {
				synchronized (objBook) {
					if (objBook != null) {
						// reserve the book
						if (objBook.getNumOfCopy() > 0) {
							objBook.setNumOfCopy(objBook.getNumOfCopy() - 1);// Decrement
																				// available
																				// copies
							(objStudent.getReservedBooks()).put(objBook,
									Default_Reserve_Period);// Add Book to
															// Student's
															// reserved list for
															// 14 days
							success = true;
							logger.info(strUsername + ": Reserved the book "
									+ strBookName + "\n. Remaining copies of"
									+ strBookName + "is/are"
									+ objBook.getNumOfCopy());
							System.out.println(this.instituteName
									+ " Library : " + strUsername
									+ ": Reserved the book " + strBookName
									+ "\n. Remaining copies of " + strBookName
									+ " is/are " + objBook.getNumOfCopy());

						} else {
							System.out
									.println("Required book not available currently");
						}
					} else {
						System.out.println("Required book not found");
					}
				}

			} else {
				if (iLoginResult == 2)
					System.out.println("Login is invalid!");
			}
		} else {
			System.out.println("Student " + strUsername + " does not exist!");
		}
		return success;
	}

	// Checks availability of requested book
	/**
	 * Checks if is book available.
	 *
	 * @param strBookName
	 *            the str book name
	 * @return true, if is book available
	 */
	private boolean isBookAvailable(String strBookName) {
		System.out.println("Checking availability of " + strBookName + " on "
				+ this.instituteName + " Server.");
		boolean isAvailable = false;
		Book objBook = tableBooks.get(strBookName);
		if (objBook != null) {
			// reserve the book
			if (objBook.getNumOfCopy() > 0) {
				isAvailable = true;
			}
		}
		return isAvailable;
	}

	// Reserves book from other libraries if Book not available locally
	/*
	 * (non-Javadoc)
	 * 
	 * @see Interface.LibraryInterface#reserveInterLibrary(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean reserveInterLibrary(String m_username, String m_password,
			String m_bookName, String m_authorName) {
		boolean bookReserved = false;
		Student objStudent = null;
		objStudent = getStudent(m_username);
		HashMap<String, Book> tableBooks = this.getBooksTable();
		HashMap<Book, Integer> reservedBooks = objStudent.getReservedBooks();
		Book book = tableBooks.get(m_bookName);
		if (reservedBooks.containsKey(book)) {
			logger.info(m_bookName + ": is already reserved for the user "
					+ m_bookName);
			System.out.println(m_bookName
					+ ": is already reserved for the user " + m_bookName);
			return bookReserved;
		}
		if (isBookAvailable(m_bookName)) {

			bookReserved = reserveBook(m_username, m_password, m_bookName,
					m_authorName);
		} else {
			System.out.println(m_bookName + " was not found. On "
					+ this.instituteName + " Server.");
			for (String libraryServer : ServerNames) {
				if (this.instituteName != libraryServer) {
					try {
						String portNumber = getPortNumber(libraryServer);
						LibraryInterface remoteServer = getService(portNumber,
								libraryServer);

						if (remoteServer
								.grantBookInterServer(m_bookName, false)) {
							// Add granted book to Students Reserved book list
							// Student objStudent = null;
							objStudent = getStudent(m_username);
							Book objBook = new Book(m_bookName, m_authorName, 0);
							try {
								(objStudent.getReservedBooks()).put(objBook,
										Default_Reserve_Period);// Add Book to
																// Student's
																// reserved list
																// for 14 days
								System.out
										.println(this.instituteName
												+ " Library : " + m_username
												+ ": reserved book "
												+ m_bookName + " from "
												+ libraryServer + " server.");
								bookReserved = true;
							} catch (Exception e) {
								// Revert back the process as book reservation
								// failed
								remoteServer.grantBookInterServer(m_bookName,
										true);
							}
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

		return bookReserved;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Interface.LibraryInterface#grantBookInterServer(java.lang.String,
	 * boolean)
	 */
	public boolean grantBookInterServer(String strBookName, boolean isRevertCall) {
		boolean isAvailable = false;
		System.out.println("This is " + this.instituteName + " Server.");
		HashMap<String, Book> BookTable = this.getBooksTable();
		Book objBook = BookTable.get(strBookName);
		synchronized (objBook) {
			if (objBook != null) {
				if (!isRevertCall)// Call for reserve book
				{
					// reserve the book
					if (objBook.getNumOfCopy() > 0) {
						objBook.setNumOfCopy(objBook.getNumOfCopy() - 1);// Decrement
																			// available
																			// copies
						isAvailable = true;
					}
				} else {
					objBook.setNumOfCopy(objBook.getNumOfCopy() + 1);// Increment
																		// available
																		// copies
					isAvailable = true;
				}
			} else {
				isAvailable = false;
			}
		}
		return isAvailable;
	}

	/**
	 * Checks if is exist.
	 *
	 * @param strUsername
	 *            the str username
	 * @param strEducationalInstitution
	 *            the str educational institution
	 * @return true, if is exist
	 */
	public boolean isExist(String strUsername, String strEducationalInstitution) {
		boolean exist = false;
		ArrayList<Student> listStudent = new ArrayList<Student>();
		listStudent = tableStudents.get(strUsername.charAt(0));
		if (listStudent != null) {
			if (listStudent.size() > 0) {
				synchronized (listStudent) {
					for (Student student : listStudent) {
						if (student.getUserName().equals(strUsername)) {
							exist = true;
						}
					}
				}
			}
		}
		return exist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Interface.LibraryInterface#checkUser(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public int checkUser(String strUsername, String strPassword,
			String strEducationalInstitution) {
		int login = 0;
		ArrayList<Student> listStudent = new ArrayList<Student>();
		listStudent = tableStudents.get(strUsername.charAt(0));
		if (listStudent != null) {
			if (listStudent.size() > 0) {
				synchronized (listStudent) {
					for (Student student : listStudent) {
						if (student.getUserName().equals(strUsername)) {
							if (student.getPassword().equals(strPassword)) {
								login = 1;
							} else {
								login = 2;
							}
						}
					}
				}
			}
		}
		return login;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Interface.LibraryInterface#getNonReturners(java.lang.String,
	 * java.lang.String, java.lang.String, int)
	 */
	@Override
	public String getNonReturners(String AdminUsername, String AdminPassword,
			String InstitutionName, int NumDays) {
		String response = "";
		if (AdminUsername.equalsIgnoreCase("admin")
				&& AdminPassword.equals("admin")) {
			response += GetNonReturnersByServer(NumDays);
			response += "\n";
			for (String Server : ServerNames) {
				synchronized (Server) {
					if (!Server.equals(this.instituteName)) {
						try {
							String portNumber = getPortNumber(Server);
							LibraryInterface remoteServer = getService(
									portNumber, Server);
							response += remoteServer
									.GetNonReturnersByServer(NumDays);
							response += "\n";
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		} else
			System.out.println("Invalid Login");
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Interface.LibraryInterface#GetNonReturnersByServer(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String GetNonReturnersByServer(int NumDays) {
		StringBuilder sbStudentList = new StringBuilder();
		sbStudentList.append(instituteName + ": \n");
		Iterator<?> it = tableStudents.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it.next();
			ArrayList<Student> listStudent = (ArrayList<Student>) pair
					.getValue();
			if (!listStudent.isEmpty()) {
				for (Student objStudent : listStudent) {
					if (!objStudent.getReservedBooks().isEmpty()) {
						Iterator<?> innerIterator = objStudent
								.getReservedBooks().entrySet().iterator();
						while (innerIterator.hasNext()) {
							@SuppressWarnings("rawtypes")
							Map.Entry innerPair = (Map.Entry) innerIterator
									.next();

							if ((int) innerPair.getValue() <= (14 - NumDays)) {
								sbStudentList.append(objStudent.getFirstName()
										+ " " + objStudent.getLastName() + " "
										+ objStudent.getPhoneNumber() + "\n");
							}
						}
					}
				}
			}
		}
		return sbStudentList.toString();
	}

	/**
	 * Gets the student.
	 *
	 * @param strUserName
	 *            the str user name
	 * @return the student
	 */
	private Student getStudent(String strUserName) {
		Student objStudent = null;
		ArrayList<Student> listStudent = tableStudents.get(strUserName
				.charAt(0));
		if (tableStudents.get(strUserName.charAt(0)) != null) {
			synchronized (tableStudents.get(strUserName.charAt(0))) {
				if (listStudent.size() > 0) {
					for (Student student : listStudent) {
						if (student.getUserName().equals(strUserName)) {
							objStudent = student;
						}
					}
				}
			}
		} else {
			if (listStudent.size() > 0) {
				for (Student student : listStudent) {
					if (student.getUserName().equals(strUserName)) {
						objStudent = student;
					}
				}
			}

		}
		return objStudent;
	}

	/**
	 * Gets the service.
	 *
	 * @param portNumber
	 *            the port number
	 * @param strInstituteName
	 *            the str institute name
	 * @return the service
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	private LibraryInterface getService(String portNumber,
			String strInstituteName) throws MalformedURLException {
		URL url = new URL("http://localhost:" + portNumber + "/"
				+ strInstituteName + "/ws?wsdl");
		QName qname = new QName("http://server/", "LibraryServerService");
		Service service = Service.create(url, qname);
		LibraryInterface library = service.getPort(LibraryInterface.class);
		return library;
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
}

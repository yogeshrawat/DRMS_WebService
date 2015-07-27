/*
 * 
 */
package LibraryObjects;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class Student.
 */
public class Student {

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The email address. */
	private String emailAddress;

	/** The phone number. */
	private String phoneNumber;

	/** The user name. */
	private String userName;

	/** The password. */
	private String password;

	/** The institute. */
	private String institute;

	/** The list reserved books. */
	private HashMap<Book, Integer> listReservedBooks;

	/** The i fines accumulated. */
	private int iFinesAccumulated;

	// ArrayList<Borrow> borrowList = new ArrayList<Borrow>();

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the email address.
	 *
	 * @param emailAddress
	 *            the new email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber
	 *            the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the inst.
	 *
	 * @return the inst
	 */
	public String getInst() {
		return institute;
	}

	/**
	 * Sets the inst.
	 *
	 * @param inst
	 *            the new inst
	 */
	public void setInst(String inst) {
		this.institute = inst;
	}

	/**
	 * Gets the reserved books.
	 *
	 * @return the reserved books
	 */
	public HashMap<Book, Integer> getReservedBooks() {
		return listReservedBooks;
	}

	/**
	 * Sets the reserved books.
	 *
	 * @param listReservedBooks
	 *            the list reserved books
	 */
	public void setReservedBooks(HashMap<Book, Integer> listReservedBooks) {
		this.listReservedBooks = listReservedBooks;
	}

	/**
	 * Gets the fines accumulated.
	 *
	 * @return the fines accumulated
	 */
	public int getFinesAccumulated() {
		return iFinesAccumulated;
	}

	/**
	 * Sets the fines accumulated.
	 *
	 * @param iFinesAccumulated
	 *            the new fines accumulated
	 */
	public void setFinesAccumulated(int iFinesAccumulated) {
		this.iFinesAccumulated = iFinesAccumulated;
	}

	/**
	 * Instantiates a new student.
	 *
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param inst
	 *            the inst
	 */
	public Student(String userName, String password, String inst) {
		this.userName = userName;
		this.password = password;
		this.institute = inst;
		listReservedBooks = new HashMap<Book, Integer>();
	}
}
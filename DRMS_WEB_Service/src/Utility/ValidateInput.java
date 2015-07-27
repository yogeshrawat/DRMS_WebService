/*
 * 
 */
package Utility;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc

/**
 * The Class ValidateInput.
 */
public class ValidateInput {

	/** The m_phone number. */
	String m_phoneNumber;

	/** The m_username. */
	static String m_username;

	/** The m_scan. */
	Scanner m_scan = new Scanner(System.in);

	/** The m_password. */
	static String m_password;

	/** The pattern. */
	private Pattern pattern;

	/** The matcher. */
	private Matcher matcher;

	/** The Constant PASSWORD_PATTERN. */
	private static final String PASSWORD_PATTERN = "((?=.*[a-z]).{6,15})";

	/**
	 * Instantiates a new validate input.
	 */
	public ValidateInput() {
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	/**
	 * Validate.
	 *
	 * @param m_password
	 *            the m_password
	 * @return the string
	 */
	public String validate(String m_password) {

		matcher = pattern.matcher(m_password);
		if (matcher.matches()) {
			return m_password;
		} else {
			System.out
					.println("*** Password Must Be atleat 6 Character Long*** Enter again:");
			m_password = m_scan.next();
			return (validate(m_password));
		}

	}

	/**
	 * Validate user name.
	 *
	 * @param m_username
	 *            the m_username
	 * @return the string
	 */
	public String validateUserName(String m_username) {

		matcher = pattern.matcher(m_username);
		if (matcher.matches()) {
			return m_username;
		} else {
			System.out
					.println("*** username Must Be atleat 6 Character Long*** Enter again:");
			m_username = m_scan.next();
			return (validateUserName(m_username));
		}

	}

	/**
	 * Validate ph no.
	 *
	 * @param m_phoneNumber
	 *            the m_phone number
	 * @return the string
	 */
	public String validatePhNo(String m_phoneNumber) {
		Pattern pattern = Pattern.compile("\\d{10}");
		Matcher matcher = pattern.matcher(m_phoneNumber);
		if (matcher.matches()) {
			return m_phoneNumber;
		} else {
			System.out.println("*** Invalid Phone Number*** Enter again:");
			m_phoneNumber = m_scan.next();
			return (validatePhNo(m_phoneNumber));
		}

	}

	/**
	 * The main method.
	 *
	 * @param argv
	 *            the arguments
	 */
	public static void main(String[] argv) {
		ValidateInput vphno = new ValidateInput();
		System.out.println("enter username an :");
		Scanner keyboard = new Scanner(System.in);
		String m_username = keyboard.next();
		System.out.println(vphno.validateUserName(m_username));
		System.out.println("enter passowrd an :");

		String m_password = keyboard.next();
		// vphno.validate(m_password);
		System.out.println(vphno.validate(m_password));
	}
}

/*
 * 
 */
package Client;

import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


// TODO: Auto-generated Javadoc
/**
 * The Class Client.
 */
public class Client {
	
	/** The logger. */
	protected Logger logger;

	/**
	 * Sets the logger.
	 *
	 * @param username the username
	 * @param fileName the file name
	 */
	public void setLogger(String username, String fileName) {
		try{
			this.logger = Logger.getLogger(username);
			FileHandler fileTxt 	 = new FileHandler(fileName);
			SimpleFormatter formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    logger.addHandler(fileTxt);
			logger.setUseParentHandlers(false);

		}
		catch(Exception err) {
			System.out.println("Couldn't create Logger. Please check file permission");
		}
	}
	
	/**
	 * Input string validation.
	 *
	 * @param keyboard the keyboard
	 * @return the string
	 */
	public String InputStringValidation(Scanner keyboard) {
		Boolean valid = false;
		String userInput = "";
		while(!valid)
		{
			try{
				userInput=keyboard.nextLine();
				valid=true;
			}
			catch(Exception e)
			{
				System.out.println("Invalid Input, please enter an String");
				valid=false;
				keyboard.nextLine();
			}
		}
		return userInput;
	}
	
	/**
	 * Input int validation.
	 *
	 * @param keyboard the keyboard
	 * @return the int
	 */
	public int InputIntValidation(Scanner keyboard) {
		Boolean valid = false;
		int userInput = 0;
		while(!valid)
		{
			try{
				userInput=keyboard.nextInt();
				valid=true;
			}
			catch(Exception e)
			{
				System.out.println("Invalid Input, please enter an Integer");
				valid=false;
				keyboard.nextLine();
			}
		}
		return userInput;
	}
}

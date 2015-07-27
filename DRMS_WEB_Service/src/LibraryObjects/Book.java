/*
 * 
 */
package LibraryObjects;
// TODO: Auto-generated Javadoc

/**
 * The Class Book.
 */
public class Book 
{
	
	/** The name. */
	private String name;
	
	/** The author. */
	private String author;
	
	/** The num of copy. */
	private int numOfCopy;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the num of copy.
	 *
	 * @return the num of copy
	 */
	public int getNumOfCopy() {
		return numOfCopy;
	}

	/**
	 * Sets the num of copy.
	 *
	 * @param numOfCopy the new num of copy
	 */
	public void setNumOfCopy(int numOfCopy) {
		this.numOfCopy = numOfCopy;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * Instantiates a new book.
	 *
	 * @param name the name
	 * @param author the author
	 * @param numOfCopy the num of copy
	 */
	public Book(String name, String author, int numOfCopy)
	{
		this.name 	= name;
		this.author = author;
		this.numOfCopy = numOfCopy;
	}
}

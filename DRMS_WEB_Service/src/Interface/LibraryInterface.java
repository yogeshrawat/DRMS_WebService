/*
 * 
 */
package Interface;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

// TODO: Auto-generated Javadoc
/**
 * The Interface LibraryInterface.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface LibraryInterface {
	
	/**
	 * Creates the account.
	 *
	 * @param m_firstName the m_first name
	 * @param m_lastName the m_last name
	 * @param m_emailAddress the m_email address
	 * @param m_phoneNumber the m_phone number
	 * @param m_username the m_username
	 * @param m_password the m_password
	 * @param m_educationalInstitution the m_educational institution
	 * @return true, if successful
	 */
	@WebMethod
	public boolean createAccount(@WebParam String m_firstName,
			@WebParam String m_lastName, @WebParam String m_emailAddress,
			@WebParam String m_phoneNumber, @WebParam String m_username,
			@WebParam String m_password,
			@WebParam String m_educationalInstitution);

	/**
	 * Reserve book.
	 *
	 * @param m_username the m_username
	 * @param m_password the m_password
	 * @param m_bookName the m_book name
	 * @param m_author the m_author
	 * @return true, if successful
	 */
	@WebMethod
	public boolean reserveBook(@WebParam String m_username,
			@WebParam String m_password, @WebParam String m_bookName,
			@WebParam String m_author);

	/**
	 * Check user.
	 *
	 * @param m_username the m_username
	 * @param m_password the m_password
	 * @param m_educationalInstitution the m_educational institution
	 * @return the int
	 */
	@WebMethod
	public int checkUser(@WebParam String m_username,
			@WebParam String m_password,
			@WebParam String m_educationalInstitution);

	/**
	 * Gets the non returners.
	 *
	 * @param AdminUsername the admin username
	 * @param strPassword the str password
	 * @param InstitutionName the institution name
	 * @param NumDays the num days
	 * @return the non returners
	 */
	@WebMethod
	public String getNonReturners(@WebParam String AdminUsername,
			@WebParam String strPassword, @WebParam String InstitutionName,
			@WebParam int NumDays);

	/**
	 * Grant book inter server.
	 *
	 * @param strBookName the str book name
	 * @param isRevertCall the is revert call
	 * @return true, if successful
	 */
	@WebMethod
	public boolean grantBookInterServer(@WebParam String strBookName,
			@WebParam boolean isRevertCall);

	/**
	 * Reserve inter library.
	 *
	 * @param m_username the m_username
	 * @param m_password the m_password
	 * @param m_bookName the m_book name
	 * @param m_authorName the m_author name
	 * @return true, if successful
	 */
	@WebMethod
	public boolean reserveInterLibrary(@WebParam String m_username,
			@WebParam String m_password, @WebParam String m_bookName,
			@WebParam String m_authorName);

	/**
	 * Gets the non returners by server.
	 *
	 * @param NumDays the num days
	 * @return the string
	 */
	@WebMethod
	public String GetNonReturnersByServer(@WebParam int NumDays);
}

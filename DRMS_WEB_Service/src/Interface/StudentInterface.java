package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface StudentInterface extends Remote{

	public boolean createAccount(String m_firstName,String m_lastName,String m_emailAddress,String m_phoneNumber,String m_username,String m_password,String m_educationalInstitution)  ;
	public boolean reserveBook(String m_username,String m_password,String m_bookName,String m_author) ;
	public int checkUser(String m_username,String m_password,String m_educationalInstitution) ;
	//public String searchBook(String strBookName) ;
}


package Interface;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface LibraryInterface{
	@WebMethod
	public boolean createAccount(@WebParam String m_firstName,@WebParam String m_lastName,@WebParam String m_emailAddress,@WebParam String m_phoneNumber,@WebParam String m_username,@WebParam String m_password,@WebParam String m_educationalInstitution)  ;
	@WebMethod
	public boolean reserveBook(@WebParam String m_username,@WebParam String m_password,@WebParam String m_bookName,@WebParam String m_author) ;
	@WebMethod
	public int checkUser(@WebParam String m_username,@WebParam String m_password,@WebParam String m_educationalInstitution) ;
	@WebMethod
	public String getNonReturners(@WebParam String AdminUsername,@WebParam String strPassword,@WebParam String InstitutionName,@WebParam int NumDays) ;	
	@WebMethod
	public boolean grantBookInterServer(@WebParam String strBookName,@WebParam boolean isRevertCall);
	@WebMethod
	public boolean reserveInterLibrary(@WebParam String m_username,@WebParam String m_password,@WebParam String m_bookName,@WebParam String m_authorName);
	@WebMethod
	public String GetNonReturnersByServer(@WebParam int NumDays);
}

